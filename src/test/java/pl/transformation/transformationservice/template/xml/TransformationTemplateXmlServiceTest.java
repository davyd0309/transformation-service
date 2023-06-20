package pl.transformation.transformationservice.template.xml;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


class TransformationTemplateXmlServiceTest {

    private final MongoTemplate mongoTemplate = Mockito.mock(MongoTemplate.class);
    private final TransformationTemplateXmlService templateService = new TransformationTemplateXmlService(mongoTemplate);

    @Test
     void testCreateTemplate_Success() {
        // given
        String xsltTemplate = "<xsl:stylesheet>...</xsl:stylesheet>";
        Document insertedDocument = new Document("_id", new ObjectId("609b2a8643e6a12345678901"))
                .append("template", xsltTemplate);
        when(mongoTemplate.insert(any(Document.class), eq("templates")))
                .thenReturn(insertedDocument);
        // when
        XSLTTemplateXml createdTemplate = templateService.createTemplate(xsltTemplate);
        // then
        assertNotNull(createdTemplate);
        assertEquals("609b2a8643e6a12345678901", createdTemplate.getId());
        assertEquals(xsltTemplate, createdTemplate.getTemplate());
        verify(mongoTemplate, times(1)).insert(any(Document.class), eq("templates"));
    }

    @Test
     void testGetAllTemplates_Success() {
        // given
        Document document1 = new Document("_id", new ObjectId("609b2a8643e6a12345678901"))
                .append("template", "<xsl:stylesheet>...</xsl:stylesheet>");
        Document document2 = new Document("_id", new ObjectId("609b2a8643e6a12345678902"))
                .append("template", "<xsl:stylesheet>...</xsl:stylesheet>");
        when(mongoTemplate.findAll(Document.class, "templates"))
                .thenReturn(Arrays.asList(document1, document2));
        // when
        List<XSLTTemplateXml> templates = templateService.getAllTemplates();
        // then
        assertNotNull(templates);
        assertEquals(2, templates.size());
        assertEquals("609b2a8643e6a12345678901", templates.get(0).getId());
        assertEquals("609b2a8643e6a12345678902", templates.get(1).getId());
        verify(mongoTemplate, times(1)).findAll(Document.class, "templates");
    }

    @Test
     void testDeleteTemplateById_Success() {
        // given
        String id = "609b2a8643e6a12345678901";
        Query expectedQuery = new Query(Criteria.where("_id").is(id));
        // when
        templateService.deleteTemplateById(id);
        // then
        verify(mongoTemplate, times(1)).remove(expectedQuery, Document.class, "templates");
    }

    @Test
     void testReplaceTemplateById_Success() {
        // given
        String id = "609b2a8643e6a12345678901";
        String xsltTemplate = "<xsl:stylesheet>...</xsl:stylesheet>";
        Document replacementDocument = new Document("template", xsltTemplate);
        Query expectedQuery = new Query(Criteria.where("_id").is(id));
        when(mongoTemplate.findAndReplace(eq(expectedQuery), eq(replacementDocument), eq("templates")))
                .thenReturn(null);
        when(mongoTemplate.findOne(eq(expectedQuery), eq(Document.class), eq("templates")))
                .thenReturn(new Document("_id", new ObjectId(id)).append("template", xsltTemplate));
        // when
        XSLTTemplateXml replacedTemplate = templateService.replaceTemplateById(id, xsltTemplate);
        // then
        assertNotNull(replacedTemplate);
        assertEquals(id, replacedTemplate.getId());
        assertEquals(xsltTemplate, replacedTemplate.getTemplate());
        verify(mongoTemplate, times(1)).findAndReplace(eq(expectedQuery), eq(replacementDocument), eq("templates"));
        verify(mongoTemplate, times(1)).findOne(eq(expectedQuery), eq(Document.class), eq("templates"));
    }

    @Test
     void testGetTemplateById_Success() {
        // given
        String id = "609b2a8643e6a12345678901";
        Query expectedQuery = new Query(Criteria.where("_id").is(id));
        Document foundDocument = new Document("_id", new ObjectId(id))
                .append("template", "<xsl:stylesheet>...</xsl:stylesheet>");
        when(mongoTemplate.findOne(eq(expectedQuery), eq(Document.class), eq("templates")))
                .thenReturn(foundDocument);
        // when
        XSLTTemplateXml template = templateService.getTemplateById(id);
        // then
        assertNotNull(template);
        assertEquals(id, template.getId());
        assertEquals("<xsl:stylesheet>...</xsl:stylesheet>", template.getTemplate());
        verify(mongoTemplate, times(1)).findOne(eq(expectedQuery), eq(Document.class), eq("templates"));
    }

}