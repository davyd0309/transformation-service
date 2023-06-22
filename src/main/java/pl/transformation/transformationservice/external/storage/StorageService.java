package pl.transformation.transformationservice.external.storage;

import org.springframework.core.io.ByteArrayResource;

public interface StorageService {
    String saveXmlFie(String filename, ByteArrayResource transformedDocument);
}
