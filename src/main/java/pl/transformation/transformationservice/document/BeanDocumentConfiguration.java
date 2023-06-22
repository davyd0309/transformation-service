package pl.transformation.transformationservice.document;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import pl.transformation.transformationservice.config.KafkaConfiguration;
import pl.transformation.transformationservice.config.MongoConfiguration;
import pl.transformation.transformationservice.external.kafka.TransformKafkaProducerService;
import pl.transformation.transformationservice.external.kafka.KafkaProducerService;
import pl.transformation.transformationservice.external.storage.StorageService;
import pl.transformation.transformationservice.external.storage.TransformStorageService;

import java.util.concurrent.*;

@Configuration
@Import({MongoConfiguration.class, KafkaConfiguration.class})
class BeanDocumentConfiguration {

    @Bean
    public ExecutorService executorService() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("thread-pool-%d")
                .build();

        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,
                10,
                1L, TimeUnit.MINUTES,
                queue,
                threadFactory
        );

        executor.allowCoreThreadTimeOut(true);

        return executor;
    }

    @Bean
    TransformationDocumentSyncService transformationDocumentSyncService(TransformProcess transformProcess) {
        return new TransformationDocumentSyncService(transformProcess);
    }

    @Bean
    TransformProcess transformProcess(MongoTemplate mongoTemplate) {
        return new TransformProcess(mongoTemplate);
    }

    @Bean
    KafkaProducerService kafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        return new TransformKafkaProducerService(kafkaTemplate);
    }

    @Bean
    StorageService storageService() {
        return new TransformStorageService();
    }

    @Bean
    TransformationDocumentAsyncService transformationDocumentAsyncService(TransformProcess transformProcess,
                                                                          ExecutorService executorService,
                                                                          KafkaProducerService kafkaProducerService,
                                                                          StorageService storageService) {
        return new TransformationDocumentAsyncService(transformProcess, executorService, kafkaProducerService, storageService);
    }
}
