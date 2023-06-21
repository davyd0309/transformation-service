package pl.transformation.transformationservice.template.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import pl.transformation.transformationservice.db.TransformationTemplateJsonRepository;
import pl.transformation.transformationservice.template.json.XSLTTemplateJson;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransformationTemplateJsonRepositoryTest {

    @Mock
    private MongoTemplate mongoTemplate;
    private TransformationTemplateJsonRepository repository;

    @BeforeEach
    void setup() {
        repository = new TransformationTemplateJsonRepository(mongoTemplate);
    }

    @Test
    void testCreateTemplate() {
        // given
        XSLTTemplateJson template = new XSLTTemplateJson("1", "template1", "Description1", "Content1");
        when(mongoTemplate.insert(template, "templates-json")).thenReturn(template);
        // when
        XSLTTemplateJson result = repository.createTemplate(template);
        // then
        verify(mongoTemplate).insert(template, "templates-json");
        assertEquals(template, result);
    }

    @Test
    void testGetAllTemplates() {
        // given
        List<XSLTTemplateJson> templates = List.of(
                new XSLTTemplateJson("1", "template1", "Description1", "Content1"),
                new XSLTTemplateJson("2", "template2", "Description2", "Content2")
        );
        when(mongoTemplate.findAll(XSLTTemplateJson.class, "templates-json")).thenReturn(templates);
        // when
        List<XSLTTemplateJson> result = repository.getAllTemplates();
        // then
        verify(mongoTemplate).findAll(XSLTTemplateJson.class, "templates-json");
        assertEquals(templates, result);
    }

    @Test
    void testGetTemplateById() {
        // given
        String id = "1";
        XSLTTemplateJson template = new XSLTTemplateJson("1", "template1", "Description1", "Content1");
        Query query = new Query(Criteria.where("_id").is(id));
        when(mongoTemplate.findOne(query, XSLTTemplateJson.class, "templates-json")).thenReturn(template);
        // when
        XSLTTemplateJson result = repository.getTemplateById(id);
        // then
        verify(mongoTemplate).findOne(query, XSLTTemplateJson.class, "templates-json");
        assertEquals(template, result);
    }

    @Test
    void testGetTemplateByFilename() {
        // given
        String filename = "template1";
        XSLTTemplateJson template = new XSLTTemplateJson("1", "template1", "Description1", "Content1");
        Query query = new Query(Criteria.where("filename").is(filename));
        when(mongoTemplate.findOne(query, XSLTTemplateJson.class, "templates-json")).thenReturn(template);
        // when
        XSLTTemplateJson result = repository.getTemplateByFilename(filename);
        // then
        verify(mongoTemplate).findOne(query, XSLTTemplateJson.class, "templates-json");
        assertEquals(template, result);
    }
}