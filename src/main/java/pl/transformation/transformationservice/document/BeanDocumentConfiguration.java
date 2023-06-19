package pl.transformation.transformationservice.document;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import pl.transformation.transformationservice.config.MongoConfiguration;

@Configuration
@Import(MongoConfiguration.class)
 class BeanDocumentConfiguration {

    @Bean
    TransformationDocumentService TransformationDocumentService(MongoTemplate mongoTemplate){
        return new TransformationDocumentService(mongoTemplate);
    }
}
