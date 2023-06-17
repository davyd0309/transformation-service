package pl.transformation.transformationservice.document;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
 class BeanDocumentConfiguration {

    @Bean
    TransformationDocumentService TransformationDocumentService(){
        return new TransformationDocumentService();
    }
}
