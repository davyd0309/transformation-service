package pl.transformation.transformationservice.template.json;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import pl.transformation.transformationservice.config.MongoConfiguration;

@Configuration
@Import(MongoConfiguration.class)
 public class BeanTemplateJsonConfiguration {

    @Bean
    public TransformationTemplateJsonService xmlTransformationTemplateService(MongoTemplate mongoTemplate){
        return new TransformationTemplateJsonService(mongoTemplate);
    }
}
