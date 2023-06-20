package pl.transformation.transformationservice.document;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import pl.transformation.transformationservice.config.MongoConfiguration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@Import(MongoConfiguration.class)
class BeanDocumentConfiguration {

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(10);
    }

    @Bean
    TransformationDocumentService TransformationDocumentService(MongoTemplate mongoTemplate, ExecutorService executorService) {
        return new TransformationDocumentService(mongoTemplate, executorService);
    }
}
