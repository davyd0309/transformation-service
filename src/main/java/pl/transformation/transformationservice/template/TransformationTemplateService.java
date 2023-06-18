package pl.transformation.transformationservice.template;

import com.mongodb.DBObject;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

class TransformationTemplateService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public XSLTTemplate createTemplate(XSLTTemplate xsltTemplate) {
        return mongoTemplate.save(xsltTemplate);
    }

    public  List<XSLTTemplate> getAllTemplates() {
        List<XSLTTemplate> templates = mongoTemplate.findAll(XSLTTemplate.class, "templates");
        return templates;
    }

    public String deleteTemplateById(String id){
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, DBObject.class, "templates");
        return id;
    }

    public String deleteTemplateByFileName(String fileName){
        Query query = new Query(Criteria.where("_filename").is(fileName));
        mongoTemplate.remove(query, DBObject.class, "templates");
        return fileName;
    }

    public String replaceTemplateById(String id, String xsltData) {
        Query query = new Query(Criteria.where("_id").is(id));
        Document replacementDocument = new Document("template", xsltData);
        mongoTemplate.findAndReplace(query, replacementDocument, "templates");
        return id;
    }

    public XSLTTemplate getTemplateById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, XSLTTemplate.class, "templates");
    }

    public List<XSLTTemplate> findByFilename(String filename) {
        Query query = new Query(Criteria.where("filename").is(filename));
        return mongoTemplate.find(query, XSLTTemplate.class);
    }
}
