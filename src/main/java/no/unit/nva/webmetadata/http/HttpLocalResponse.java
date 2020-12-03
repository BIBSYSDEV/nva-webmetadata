package no.unit.nva.webmetadata.http;

public class HttpLocalResponse {
    private final String body;
    private final int statusCode;

    public HttpLocalResponse(int statusCode, String body) {
        this.body = body;
        this.statusCode = statusCode;
    }

    public String getBody() {
        return body;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
