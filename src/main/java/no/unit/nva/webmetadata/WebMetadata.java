package no.unit.nva.webmetadata;

import io.quarkus.funqy.Funq;
import no.unit.nva.webmetadata.http.HttpService;

import java.io.IOException;
import java.net.URI;

public class WebMetadata {

    public static final int STATUS_OK = 200;
    /* default */ final HttpService httpService;

    public WebMetadata(HttpService httpService) {
        this.httpService = httpService;
    }

    /**
     * Function takes a URI and dereferences it, extracting relevant metadata, returning a Publication.
     * @param uri Any valid URI.
     * @return Publication object.
     * @throws IOException If the HTTP request for the URI fails.
     * @throws InterruptedException If the connection is interrupted.
     */
    @Funq("metadata")
    public String getMetadata(URI uri) throws IOException, InterruptedException {
        /* default */ var httpResponse = httpService.get(uri);
        if (httpResponse.getStatusCode() != STATUS_OK) {
            throw new RuntimeException("Error status: " + httpResponse.getStatusCode());
        }
        return httpResponse.getBody();
    }
}
