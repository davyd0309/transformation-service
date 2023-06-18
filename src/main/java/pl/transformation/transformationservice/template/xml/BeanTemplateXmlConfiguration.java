package pl.transformation.transformationservice.template.xml;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
 class BeanTemplateXmlConfiguration {

    @Bean
    TransformationTemplateXmlService transformationTemplateXmlService(){
        return new TransformationTemplateXmlService();
    }
}
