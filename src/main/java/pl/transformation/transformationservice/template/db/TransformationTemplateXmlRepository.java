package pl.transformation.transformationservice.template.db;

import org.apache.logging.log4j.util.Strings;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import pl.transformation.transformationservice.template.xml.XSLTTemplateXml;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class TransformationTemplateXmlRepository implements TransformationTemplateRepository<XSLTTemplateXml> {

    private final MongoTemplate mongoTemplate;

    public TransformationTemplateXmlRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public XSLTTemplateXml createTemplate(XSLTTemplateXml template) {
        Document document = mongoTemplate.insert(new Document("template", template.getTemplate()), "templates");
        return new XSLTTemplateXml(document.getObjectId("_id").toHexString(), document.getString("template"));
    }

    @Override
    public List<XSLTTemplateXml> getAllTemplates() {
        return mongoTemplate.findAll(Document.class, "templates")
                .stream()
                .map(document -> new XSLTTemplateXml(document.getObjectId("_id").toHexString(), document.getString("template")))
                .collect(Collectors.toList());
    }

    @Override
    public Long deleteTemplateById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.remove(query, Document.class, "templates").getDeletedCount();
    }

    @Override
    public Long deleteTemplateByFileName(String filename) {
        Query query = new Query(Criteria.where("filename").is(filename));
        return mongoTemplate.remove(query, Document.class, "templates").getDeletedCount();
    }

    @Override
    public XSLTTemplateXml replaceTemplateById(String id, XSLTTemplateXml template) {
        Query query = new Query(Criteria.where("_id").is(id));
        Document replacementDocument = new Document("template", template.getTemplate());
        mongoTemplate.findAndReplace(query, replacementDocument, "templates");
        return Optional.ofNullable(mongoTemplate.findOne(query, Document.class, "templates"))
                .map(document -> new XSLTTemplateXml(document.getObjectId("_id").toHexString(), document.getString("template")))
                .orElse(new XSLTTemplateXml(Strings.EMPTY));
    }

    @Override
    public XSLTTemplateXml getTemplateByFilename(String filename) {
        Query query = new Query(Criteria.where("filename").is(filename));
        return mongoTemplate.findOne(query, XSLTTemplateXml.class, "templates-json");
    }

    @Override
    public XSLTTemplateXml getTemplateById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return Optional.ofNullable(mongoTemplate.findOne(query, Document.class, "templates"))
                .map(document -> new XSLTTemplateXml(document.getObjectId("_id").toHexString(), document.getString("template")))
                .orElse(new XSLTTemplateXml(Strings.EMPTY));
    }
}
