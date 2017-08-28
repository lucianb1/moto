package ro.motorzz.test.edgeserver;

import java.util.Map;

/**
 * Created by Luci on 05-Mar-17.
 */
public class EdgeServerRequest {

    private final String url;
    private final String token;
    private final EdgeServerRequestMethod method;
    private final Object body;
    private final Map<String, String> params;

    EdgeServerRequest(String url, String token, EdgeServerRequestMethod method, Object body, Map<String, String> params) {
        this.url = url;
        this.token = token;
        this.method = method;
        this.body = body;
        this.params = params;
    }

    public String getUrl() {
        return url;
    }

    public String getToken() {
        return token;
    }

    public EdgeServerRequestMethod getMethod() {
        return method;
    }

    public Object getBody() {
        return body;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
