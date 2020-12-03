package no.unit.nva.webmetadata;

import io.quarkus.amazon.lambda.test.LambdaClient;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import no.unit.nva.webmetadata.http.HttpService;
import no.unit.nva.webmetadata.http.HttpLocalResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@QuarkusTest
public class WebMetadataTest {

    public static final String EXAMPLE_URI = "https://example.org/v1/largevalue";
    public static final String TEST_BODY = "<Hello Bill>";
    public static final int STATUS_CODE_OK = 200;

    @InjectMock
    HttpService httpService;

    @Test
    public void testSimpleLambdaSuccess() throws IOException, InterruptedException {
        when(httpService.get(any(URI.class))).thenReturn(new HttpLocalResponse(STATUS_CODE_OK, TEST_BODY));
        URI uri = URI.create(EXAMPLE_URI);
        String out = LambdaClient.invoke(String.class, uri);
        Assertions.assertEquals(TEST_BODY, out);
    }
}
