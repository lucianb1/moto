package ro.motorzz.test.mock;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Luci on 21-Jun-17.
 */
public class EdgeServerRequestBuilder {

    private String url;
    private String token;
    private EdgeServerRequestMethod method;
    private Object body;
    private Map<String, String> params = new HashMap<>();

    public EdgeServerRequest build() {
        return new EdgeServerRequest(url, token, method, body, params);
    }

    public EdgeServerRequestBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public EdgeServerRequestBuilder setToken(String token) {
        this.token = token;
        return this;
    }

    public EdgeServerRequestBuilder setMethod(EdgeServerRequestMethod method) {
        this.method = method;
        return this;
    }

    public EdgeServerRequestBuilder setBody(Object body) {
        this.body = body;
        return this;
    }

    public EdgeServerRequestBuilder addParam(String key, String value) {
        this.params.put(key, value);
        return this;
    }


}
