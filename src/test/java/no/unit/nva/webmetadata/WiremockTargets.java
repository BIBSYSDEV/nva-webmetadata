package no.unit.nva.webmetadata;


import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.Collections;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static no.unit.nva.webmetadata.WebMetadataTest.HIWIRE_URI_TEMPLATE;

public class WiremockTargets implements QuarkusTestResourceLifecycleManager {

    private WireMockServer wireMockServer;


    @Override
    public Map<String, String> start() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        var body = getBody("/hiwire_test_data.html");
        stubFor(get(urlEqualTo(HIWIRE_URI_TEMPLATE))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/html")
                        .withBody(body)));

        stubFor(get(urlMatching(".*"))
                .atPriority(10).willReturn(aResponse()
                        .proxiedFrom("https://example.org/article/hiwire.html")));

        return Collections.singletonMap("no.unit.nva.webmetadata.TransformerService/mp-rest/url",
                wireMockServer.baseUrl());

    }

    @Override
    public void stop() {
        if (null != wireMockServer) {
            wireMockServer.stop();
        }
    }

    private String getBody(String filename) {
        return getClass().getResourceAsStream(filename).toString();
    }
}
