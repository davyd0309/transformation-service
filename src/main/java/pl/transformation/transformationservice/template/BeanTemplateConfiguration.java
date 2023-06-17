package pl.transformation.transformationservice.template;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
 class BeanTemplateConfiguration {

    @Bean
    TransformationTemplateService xmlTransformationTemplateService(){
        return new TransformationTemplateService();
    }
}
