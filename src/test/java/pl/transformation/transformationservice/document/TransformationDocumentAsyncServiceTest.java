package pl.transformation.transformationservice.document;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.transformation.transformationservice.external.kafka.KafkaProducerService;
import pl.transformation.transformationservice.external.storage.StorageService;

import java.util.concurrent.ExecutorService;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransformationDocumentAsyncServiceTest {

    @Mock
    private TransformProcess transformProcess;

    @Mock
    private ExecutorService executorService;

    @Mock
    private KafkaProducerService kafkaProducerService;

    @Mock
    private StorageService storageService;

    private TransformationDocumentAsyncService transformationDocumentAsyncService;

    @BeforeEach
    public void setUp() {
        transformationDocumentAsyncService = new TransformationDocumentAsyncService(transformProcess,
                executorService, kafkaProducerService, storageService);
    }

    @Test
    public void shutdownExecutorService_ShouldShutdownExecutorService() {
        // when
        transformationDocumentAsyncService.shutdownExecutorService();
        // then
        verify(executorService).shutdown();
    }
}