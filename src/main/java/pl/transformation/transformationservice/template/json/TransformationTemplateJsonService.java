package pl.transformation.transformationservice.template.json;

import com.mongodb.DBObject;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

public class TransformationTemplateJsonService {

    private final MongoTemplate mongoTemplate;

    public TransformationTemplateJsonService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public XSLTTemplateJson createTemplate(XSLTTemplateJson xsltTemplateJson) {
        return mongoTemplate.insert(xsltTemplateJson);
    }

    public  List<XSLTTemplateJson> getAllTemplates() {
       return mongoTemplate.findAll(XSLTTemplateJson.class, "templates-json");
    }

    public Long deleteTemplateById(String id){
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.remove(query, XSLTTemplateJson.class, "templates-json").getDeletedCount();
    }

    public Long deleteTemplateByFileName(String filename){
        Query query = new Query(Criteria.where("filename").is(filename));
        return  mongoTemplate.remove(query, XSLTTemplateJson.class, "templates-json").getDeletedCount();
    }

    public XSLTTemplateJson replaceTemplateById(String id, XSLTTemplateJson xsltTemplateJson) {
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.findAndReplace(query, xsltTemplateJson, "templates-json");
        return mongoTemplate.findOne(query, XSLTTemplateJson.class, "templates-json");
    }

    public XSLTTemplateJson getTemplateById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, XSLTTemplateJson.class, "templates-json");
    }

    public XSLTTemplateJson getTemplateByFilename(String filename) {
        Query query = new Query(Criteria.where("filename").is(filename));
        return mongoTemplate.find(query, XSLTTemplateJson.class).stream()
                .findFirst()
                .orElse(null); // TODO change to return Optional
    }
}
