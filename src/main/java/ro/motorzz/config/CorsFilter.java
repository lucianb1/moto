package ro.motorzz.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Luci on 23-Jun-17.
 */
@Component
public class CorsFilter implements Filter {

     private static final Logger LOGGER = LoggerFactory.getLogger(CorsFilter.class);

    public CorsFilter() {
        LOGGER.info("Cors filter initilaized");
    }

    @Override
    public void init(final FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        LOGGER.info("Request intercepted by the CORS filter for: " + request.getRequestURI());
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin") == null ? "http://localhost:9000"
            : request.getHeader("Origin").isEmpty() ? "http://localhost:9000" : request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers",
            "Content-Type, X-Requested-With, X-Custom-Header, Authorization");
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(200);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
