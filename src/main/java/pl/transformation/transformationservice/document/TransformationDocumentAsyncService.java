package pl.transformation.transformationservice.document;

import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import pl.transformation.transformationservice.external.kafka.KafkaProducerService;
import pl.transformation.transformationservice.external.storage.StorageService;

import java.util.concurrent.ExecutorService;

public class TransformationDocumentAsyncService {

    private static final Logger log = LoggerFactory.getLogger(TransformationDocumentAsyncService.class);

    private final TransformProcess transformProcess;
    private final ExecutorService executorService;
    private final KafkaProducerService kafkaProducerService;
    private final StorageService storageService;

    public TransformationDocumentAsyncService(TransformProcess transformProcess,
                                              ExecutorService executorService,
                                              KafkaProducerService kafkaProducerService,
                                              StorageService storageService) {
        this.transformProcess = transformProcess;
        this.executorService = executorService;
        this.kafkaProducerService = kafkaProducerService;
        this.storageService = storageService;
    }

    public void transformXml(DocumentData documentData) {
        executorService.submit(() -> {
            ByteArrayResource transformedDocument = transformProcess.transformXml(documentData);
            if (transformedDocument != null) {
                String fileId = saveTransformedDocument(transformedDocument);
                informServicesToTransformEnd(fileId);
            }
        });
    }

    @PreDestroy
    public void shutdownExecutorService() {
        executorService.shutdown();
    }

    private String saveTransformedDocument(ByteArrayResource transformedDocument) {
        return storageService.saveXmlFie("transformedXml", transformedDocument);
    }

    private void informServicesToTransformEnd(String fileId) {
        String topic = "generated-xml-topic";
        String message = String.format("XML file with fileId %s was generated and saved", fileId);
        kafkaProducerService.send(topic, message);
    }
}
