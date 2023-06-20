package pl.transformation.transformationservice.template.xml;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransformationTemplateXmlService {

    private final MongoTemplate mongoTemplate;

    public TransformationTemplateXmlService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public XSLTTemplateXml createTemplate(String xsltTemplate) {
        Document document = mongoTemplate.insert(new Document("template",xsltTemplate), "templates");
        return new XSLTTemplateXml(document.getObjectId("_id").toHexString(), document.getString("template"));
    }

    public  List<XSLTTemplateXml> getAllTemplates() {
       return mongoTemplate.findAll(Document.class, "templates")
               .stream().map(document -> new XSLTTemplateXml(document.getObjectId("_id").toHexString(),
                       document.getString("template"))) .collect(Collectors.toList());
    }

    public void deleteTemplateById(String id){
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, Document.class, "templates");
    }

    public XSLTTemplateXml replaceTemplateById(String id, String xsltTemplate) {
        Query query = new Query(Criteria.where("_id").is(id));
        Document replacementDocument = new Document("template", xsltTemplate);
        mongoTemplate.findAndReplace(query, replacementDocument, "templates");
        return Optional.ofNullable(mongoTemplate.findOne(query, Document.class, "templates"))
                .map(document -> new XSLTTemplateXml(document.getObjectId("_id").toHexString(), document.getString("template")))
                .orElse(new XSLTTemplateXml());
    }

    public XSLTTemplateXml getTemplateById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return Optional.ofNullable(mongoTemplate.findOne(query, Document.class, "templates"))
                .map(document -> new XSLTTemplateXml(document.getObjectId("_id").toHexString(), document.getString("template")))
                .orElse(new XSLTTemplateXml());
    }
}
