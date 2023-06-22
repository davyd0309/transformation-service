package pl.transformation.transformationservice.document;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ByteArrayResource;


class TransformationDocumentSyncServiceTest {

    @Mock
    private TransformProcess transformProcess;
    private TransformationDocumentSyncService transformationDocumentSyncService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        transformationDocumentSyncService = new TransformationDocumentSyncService(transformProcess);
    }

    @Test
    public void testTransformXml_ReturnsTransformedDocument() {
        // given
        DocumentData documentData = new DocumentData("xmlData", "savedType", true, "templateId");
        ByteArrayResource transformedDocument = new ByteArrayResource("transformedXmlData".getBytes());
        Mockito.when(transformProcess.transformXml(documentData)).thenReturn(transformedDocument);
        // when
        ByteArrayResource result = transformationDocumentSyncService.transformXml(documentData);
        // then
        Assertions.assertEquals(transformedDocument, result);
        Mockito.verify(transformProcess).transformXml(documentData);
    }

    @Test
    public void testTransformXml_ReturnsNullWhenTransformProcessReturnsNull() {
        // given
        DocumentData documentData = new DocumentData("xmlData", "savedType", true, "templateId");
        Mockito.when(transformProcess.transformXml(documentData)).thenReturn(null);
        // when
        ByteArrayResource result = transformationDocumentSyncService.transformXml(documentData);
        // then
        Assertions.assertNull(result);
        Mockito.verify(transformProcess).transformXml(documentData);
    }
}