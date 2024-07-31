package http;

import java.util.Map;

public class HttpRequest {

    private final HttpMethod httpMethod;
    private final String path;
    private final Map<String, String> postParams;

    public HttpRequest(HttpMethod httpMethod, String path, Map<String, String> postParams) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.postParams = postParams;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getPostParams() {
        return postParams;
    }
}
