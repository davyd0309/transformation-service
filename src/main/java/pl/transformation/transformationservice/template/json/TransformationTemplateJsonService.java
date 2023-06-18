package pl.transformation.transformationservice.template.json;

import com.mongodb.DBObject;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

class TransformationTemplateJsonService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public XSLTTemplate createTemplate(XSLTTemplate xsltTemplate) {
        XSLTTemplate save = mongoTemplate.insert(xsltTemplate);
        return save;
    }

    public  List<XSLTTemplate> getAllTemplates() {
       return mongoTemplate.findAll(XSLTTemplate.class, "templates-json");
    }

    public void deleteTemplateById(String id){
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, XSLTTemplate.class, "templates-json");
    }

    public void deleteTemplateByFileName(String fileName){
        Query query = new Query(Criteria.where("_filename").is(fileName));
        mongoTemplate.remove(query, DBObject.class, "templates-json");
    }

    public XSLTTemplate replaceTemplateById(String id, XSLTTemplate xsltTemplate) {
        Query query = new Query(Criteria.where("_id").is(id));
        Document replacementDocument = new Document("template", xsltTemplate);
        return mongoTemplate.findAndReplace(query, xsltTemplate, "templates-json");
    }

    public XSLTTemplate getTemplateById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, XSLTTemplate.class, "templates-json");
    }

    public XSLTTemplate getTemplateByFilename(String filename) {
        Query query = new Query(Criteria.where("filename").is(filename));
        return mongoTemplate.find(query, XSLTTemplate.class).stream()
                .findFirst()
                .orElse(null);
    }
}
