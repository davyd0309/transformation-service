package pl.transformation.transformationservice.template.xml;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import pl.transformation.transformationservice.config.MongoConfiguration;

@Configuration
@Import(MongoConfiguration.class)
 public class BeanTemplateXmlConfiguration {

    @Bean
    public TransformationTemplateXmlService transformationTemplateXmlService(MongoTemplate mongoTemplate){
        return new TransformationTemplateXmlService(mongoTemplate);
    }
}
