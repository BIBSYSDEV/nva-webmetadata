package no.unit.nva.webmetadata.http;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@ApplicationScoped
public class HttpService {
    /**
     * Takes a URI, dereferences it and returns the status and body wrapped in a HttpLocalResponse.
     * @param uri Any URI.
     * @return HttpLocalResponse containing the server status code and body as string.
     * @throws IOException If the IO fails.
     * @throws InterruptedException If the request is interrupted.
     */
    public HttpLocalResponse get(URI uri) throws IOException, InterruptedException {
        var httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        var httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        var httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return new HttpLocalResponse(httpResponse.statusCode(), httpResponse.body());
    }
}
