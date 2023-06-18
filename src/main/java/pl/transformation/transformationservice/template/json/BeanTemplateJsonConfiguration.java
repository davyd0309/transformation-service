package pl.transformation.transformationservice.template.json;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
 class BeanTemplateJsonConfiguration {

    @Bean
    TransformationTemplateJsonService xmlTransformationTemplateService(){
        return new TransformationTemplateJsonService();
    }
}
