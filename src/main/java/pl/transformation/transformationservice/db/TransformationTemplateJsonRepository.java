package pl.transformation.transformationservice.db;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import pl.transformation.transformationservice.template.TransformationTemplateRepository;
import pl.transformation.transformationservice.template.json.XSLTTemplateJson;

import java.util.List;

public class TransformationTemplateJsonRepository implements TransformationTemplateRepository<XSLTTemplateJson> {

    private final MongoTemplate mongoTemplate;

    public TransformationTemplateJsonRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public XSLTTemplateJson createTemplate(XSLTTemplateJson template) {
        return mongoTemplate.insert(template, "templates-json");
    }

    @Override
    public List<XSLTTemplateJson> getAllTemplates() {
        return mongoTemplate.findAll(XSLTTemplateJson.class, "templates-json");
    }

    @Override
    public Long deleteTemplateById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.remove(query, XSLTTemplateJson.class, "templates-json").getDeletedCount();
    }

    @Override
    public Long deleteTemplateByFileName(String filename) {
        Query query = new Query(Criteria.where("filename").is(filename));
        return mongoTemplate.remove(query, XSLTTemplateJson.class, "templates-json").getDeletedCount();
    }

    @Override
    public XSLTTemplateJson replaceTemplateById(String id, XSLTTemplateJson template) {
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.findAndReplace(query, template, "templates-json");
        return mongoTemplate.findOne(query, XSLTTemplateJson.class, "templates-json");
    }

    @Override
    public XSLTTemplateJson getTemplateById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, XSLTTemplateJson.class, "templates-json");
    }

    @Override
    public XSLTTemplateJson getTemplateByFilename(String filename) {
        Query query = new Query(Criteria.where("filename").is(filename));
        return mongoTemplate.findOne(query, XSLTTemplateJson.class, "templates-json");
    }
}
