package no.unit.nva.webmetadata;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.amazon.lambda.test.LambdaClient;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

@QuarkusTest
public class WebMetadataTest {

    public static final String HIWIRE_URI_TEMPLATE = "http://localhost:%d/article/hiwire.html";

    WireMockServer wireMockServer;

    @Test
    public void testSimpleLambdaSuccess() {
        startMock();
        var testBody = getBody("/highwire_output.json");
        var uriString = String.format(HIWIRE_URI_TEMPLATE, wireMockServer.port());

        URI uri = URI.create(uriString);
        String out = LambdaClient.invoke(String.class, uri);
        Assertions.assertEquals(testBody, out);
        wireMockServer.stop();
    }

    private void startMock() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        var body = getBody("/hiwire_test_data.html");
        configureFor("localhost", wireMockServer.port());
        stubFor(get(urlEqualTo("/article/hiwire.html"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/html")
                        .withBody(body)));
    }

    private String getBody(String filename) {
        var inputStream = getClass().getResourceAsStream(filename);
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
                .collect(Collectors.joining("\n"));
    }
}
