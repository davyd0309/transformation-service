package pl.transformation.transformationservice.external.storage;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;

import static org.junit.jupiter.api.Assertions.*;

class TransformStorageServiceTest {

    private final TransformStorageService storageService = new TransformStorageService();

    @Test
    void saveXmlFile_ShouldReturnFileId() {
        // given
        String filename = "test.xml";
        byte[] fileContent = "Test content".getBytes();
        ByteArrayResource transformedDocument = new ByteArrayResource(fileContent);
        // when
        String fileId = storageService.saveXmlFie(filename, transformedDocument);
        // then
        assertEquals("fileId", fileId);
    }
}