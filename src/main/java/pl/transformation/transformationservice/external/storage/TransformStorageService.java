package pl.transformation.transformationservice.external.storage;

import org.springframework.core.io.ByteArrayResource;

public class TransformStorageService implements StorageService {
    @Override
    public String saveXmlFie(String filename, ByteArrayResource transformedDocument) {
        return "fileId";
    }
}
