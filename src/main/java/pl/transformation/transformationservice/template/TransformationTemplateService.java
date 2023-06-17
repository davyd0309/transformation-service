package pl.transformation.transformationservice.template;

import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

class TransformationTemplateService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public String createTemplate(String xsltData) {
        Document xsltDocument = new Document("template", xsltData);
        Document collection = mongoTemplate.save(xsltDocument, "collection");
        return collection.toString();
    }

    public  List<DBObject> getAllTemplates() {
        List<DBObject> collection = mongoTemplate.findAll(DBObject.class, "collection");
        return collection;
    }

}
