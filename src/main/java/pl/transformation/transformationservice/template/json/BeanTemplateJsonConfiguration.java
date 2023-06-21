package pl.transformation.transformationservice.template.json;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import pl.transformation.transformationservice.config.MongoConfiguration;
import pl.transformation.transformationservice.db.TransformationTemplateJsonRepository;
import pl.transformation.transformationservice.template.TransformationTemplateRepository;

@Configuration
@Import(MongoConfiguration.class)
public class BeanTemplateJsonConfiguration {

    @Bean
    public TransformationTemplateJsonService xmlTransformationTemplateService(TransformationTemplateRepository<XSLTTemplateJson> transformationTemplateRepository) {
        return new TransformationTemplateJsonService(transformationTemplateRepository);
    }

    @Bean
    public TransformationTemplateJsonRepository transformationTemplateJsonRepository(MongoTemplate mongoTemplate) {
        return new TransformationTemplateJsonRepository(mongoTemplate);
    }
}
