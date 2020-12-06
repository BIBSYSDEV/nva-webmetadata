package no.unit.nva.webmetadata;

import io.quarkus.funqy.Funq;
import no.unit.nva.webmetadata.http.MetadataService;
import org.apache.any23.extractor.ExtractionException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebMetadata {

    /**
     * Extracts metadata from supplied URI, returning a publication.
     * @param uri A URI providing a HTML metadata source.
     * @return An NVA publication.
     * @throws IOException If IO for resources files fails.
     * @throws ExtractionException If metadata extraction fails.
     * @throws URISyntaxException If the URI is not properly formed.
     */
    @Funq("metadata")
    public String getMetadata(URI uri) throws IOException, ExtractionException, URISyntaxException {
        return new MetadataService().getMetadata(uri);
    }
}
