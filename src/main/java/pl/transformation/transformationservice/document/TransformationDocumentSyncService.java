package pl.transformation.transformationservice.document;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;

public class TransformationDocumentSyncService {

    private static final Logger log = LoggerFactory.getLogger(TransformationDocumentSyncService.class);

    private final TransformProcess transformProcess;

    public TransformationDocumentSyncService(TransformProcess transformProcess) {
        this.transformProcess = transformProcess;
    }

    public ByteArrayResource transformXml(DocumentData documentData) {
        return transformProcess.transformXml(documentData);
    }
}
