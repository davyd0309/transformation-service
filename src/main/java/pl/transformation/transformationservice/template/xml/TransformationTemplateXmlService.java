package pl.transformation.transformationservice.template.xml;

import com.mongodb.DBObject;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class TransformationTemplateXmlService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public DocumentDto createTemplate(String xsltTemplate) {
        Document document = mongoTemplate.insert(new Document("template",xsltTemplate), "templates");
        return new DocumentDto(document.getObjectId("_id").toHexString(), document.getString("template"));
    }

    public  List<DocumentDto> getAllTemplates() {
       return mongoTemplate.findAll(Document.class, "templates")
               .stream().map(document -> new DocumentDto(document.getObjectId("_id").toHexString(),
                       document.getString("template"))) .collect(Collectors.toList());
    }

    public void deleteTemplateById(String id){
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, Document.class, "templates");
    }

    public DocumentDto replaceTemplateById(String id, String xsltTemplate) {
        Query query = new Query(Criteria.where("_id").is(id));
        Document replacementDocument = new Document("template", xsltTemplate);
        mongoTemplate.findAndReplace(query, replacementDocument, "templates");
        return Optional.ofNullable(mongoTemplate.findOne(query, Document.class, "templates"))
                .map(document -> new DocumentDto(document.getObjectId("_id").toHexString(), document.getString("template")))
                .orElse(new DocumentDto());
    }

    public DocumentDto getTemplateById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return Optional.ofNullable(mongoTemplate.findOne(query, Document.class, "templates"))
                .map(document -> new DocumentDto(document.getObjectId("_id").toHexString(), document.getString("template")))
                .orElse(new DocumentDto());
    }
}
