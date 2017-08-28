package ro.motorzz.test.edgeserver;

import com.fasterxml.jackson.databind.JavaType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ro.motorzz.core.utils.JsonUtils;

import javax.servlet.Filter;
import java.util.Iterator;
import java.util.Map;

public class EdgeServer {

    private static final JsonUtils jsonUtils = new JsonUtils();
    private MockMvc mockMvc;
    private String tokenHeader = "Authorization";

    public EdgeServer(WebApplicationContext context, Filter securityFilterChain) {
        this.mockMvc = ((DefaultMockMvcBuilder) MockMvcBuilders.webAppContextSetup(context)
                .addFilter(securityFilterChain)).build();
    }

    public <T> EdgeServerResponse<T> executeRequest(EdgeServerRequest request, Class<T> responseType) {
        MockHttpServletRequestBuilder mockRequest = this.composeMockRequest(request);

        try {
            MvcResult mvcResult = this.mockMvc.perform(mockRequest).andReturn();
            MockHttpServletResponse response = mvcResult.getResponse();
            T result = null;
            if (response.getStatus() == HttpStatus.OK.value() && StringUtils.isNotBlank(response.getContentAsString()) && responseType != null) {
                result = jsonUtils.fromJson(response.getContentAsString(), responseType);
            }

            return new EdgeServerResponse(response.getStatus(), result);
        } catch (Exception var7) {
            throw new RuntimeException(var7);
        }
    }

    public <T> EdgeServerResponse<T> executeRequest(EdgeServerRequest request, JavaType javaResponseType) {
        MockHttpServletRequestBuilder mockRequest = this.composeMockRequest(request);

        try {
            MvcResult mvcResult = this.mockMvc.perform(mockRequest).andReturn();
            MockHttpServletResponse response = mvcResult.getResponse();
            T result = null;
            if (response.getStatus() == HttpStatus.OK.value() && StringUtils.isNotBlank(response.getContentAsString()) && javaResponseType != null) {
                result = jsonUtils.fromJson(response.getContentAsString(), javaResponseType);
            }

            return new EdgeServerResponse(response.getStatus(), result);
        } catch (Exception var7) {
            throw new RuntimeException(var7);
        }
    }

    private MockHttpServletRequestBuilder composeMockRequest(EdgeServerRequest request) {
        MockHttpServletRequestBuilder mockRequest;
        switch(request.getMethod()) {
            case GET:
                mockRequest = MockMvcRequestBuilders.get(request.getUrl());
                break;
            case POST:
                mockRequest = MockMvcRequestBuilders.post(request.getUrl());
                break;
            case PUT:
                mockRequest = MockMvcRequestBuilders.put(request.getUrl());
                break;
            case DELETE:
                mockRequest = MockMvcRequestBuilders.delete(request.getUrl());
                break;
            case UPLOAD:
                throw new RuntimeException("Upload mocking is not implemented yet");
            default:
                throw new RuntimeException("Invalid method value");
        }

        mockRequest.accept(MediaType.APPLICATION_JSON);
        mockRequest.contentType(MediaType.APPLICATION_JSON);
        if (request.getBody() != null) {
            mockRequest.content(jsonUtils.toJson(request.getBody()));
        }

        if (request.getToken() != null) {
            mockRequest.header(this.tokenHeader, request.getToken());
        }

        Map<String, String> params = request.getParams();
        Iterator paramsIterator = params.entrySet().iterator();

        while(paramsIterator.hasNext()) {
            Map.Entry entry = (Map.Entry)paramsIterator.next();
            mockRequest.param((String)entry.getKey(), (String)entry.getValue());
        }

        return mockRequest;
    }

    public EdgeServer setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
        return this;
    }
}
