package pl.transformation.transformationservice.template.xml;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import pl.transformation.transformationservice.config.MongoConfiguration;
import pl.transformation.transformationservice.template.TransformationTemplateRepository;
import pl.transformation.transformationservice.db.TransformationTemplateXmlRepository;

@Configuration
@Import(MongoConfiguration.class)
public class BeanTemplateXmlConfiguration {

    @Bean
    public TransformationTemplateXmlService transformationTemplateXmlService(TransformationTemplateRepository<XSLTTemplateXml> transformationTemplateRepository) {
        return new TransformationTemplateXmlService(transformationTemplateRepository);
    }

    @Bean
    public TransformationTemplateXmlRepository transformationTemplateXmlRepository(MongoTemplate mongoTemplate) {
        return new TransformationTemplateXmlRepository(mongoTemplate);
    }
}
