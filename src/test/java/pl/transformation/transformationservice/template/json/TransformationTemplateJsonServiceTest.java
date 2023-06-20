package pl.transformation.transformationservice.template.json;

import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

class TransformationTemplateJsonServiceTest {

    private final MongoTemplate mongoTemplate = Mockito.mock(MongoTemplate.class);
    private final TransformationTemplateJsonService templateService = new TransformationTemplateJsonService(mongoTemplate);

    @Test
    void testCreateTemplate() {
        // given
        XSLTTemplateJson templateJson = new XSLTTemplateJson("1", "FileName", "Descr", "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:template match=\"/root\">...</xsl:template></xsl:stylesheet>");
        when(mongoTemplate.insert(eq(templateJson))).thenReturn(templateJson);
        // when
        XSLTTemplateJson result = templateService.createTemplate(templateJson);
        // then
        assertThat(result).isEqualTo(templateJson);
        verify(mongoTemplate).insert(eq(templateJson));
    }

    @Test
    void testGetAllTemplates() {
        // given
        XSLTTemplateJson templateJson1 = new XSLTTemplateJson("1", "FileName", "Descr", "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:template match=\"/root\">...</xsl:template></xsl:stylesheet>");
        XSLTTemplateJson templateJson2 = new XSLTTemplateJson("2", "FileName", "Descr", "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:template match=\"/root\">...</xsl:template></xsl:stylesheet>");
        List<XSLTTemplateJson> templates = Arrays.asList(templateJson1, templateJson2);
        when(mongoTemplate.findAll(eq(XSLTTemplateJson.class), eq("templates-json"))).thenReturn(templates);
        // when
        List<XSLTTemplateJson> result = templateService.getAllTemplates();
        // then
        assertThat(result).isEqualTo(templates);
        verify(mongoTemplate).findAll(eq(XSLTTemplateJson.class), eq("templates-json"));
    }

    @Test
    void testReplaceTemplateById() {
        // given
        String id = "123";
        XSLTTemplateJson templateJson1 = new XSLTTemplateJson("123", "FileName", "Descr", "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:template match=\"/root\">...</xsl:template></xsl:stylesheet>");
        Query query = new Query(Criteria.where("_id").is(id));
        when(mongoTemplate.findAndReplace(eq(query), eq(templateJson1), eq("templates-json"))).thenReturn(null);
        when(mongoTemplate.findOne(eq(query), eq(XSLTTemplateJson.class), eq("templates-json"))).thenReturn(templateJson1);
        // when
        XSLTTemplateJson result = templateService.replaceTemplateById(id, templateJson1);
        // then
        assertThat(result).isEqualTo(templateJson1);
        verify(mongoTemplate).findAndReplace(eq(query), eq(templateJson1), eq("templates-json"));
        verify(mongoTemplate).findOne(eq(query), eq(XSLTTemplateJson.class), eq("templates-json"));
    }

    @Test
    void testGetTemplateById() {
        // given
        String id = "123";
        XSLTTemplateJson templateJson1 = new XSLTTemplateJson("123", "FileName", "Descr", "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:template match=\"/root\">...</xsl:template></xsl:stylesheet>");
        Query query = new Query(Criteria.where("_id").is(id));
        when(mongoTemplate.findOne(eq(query), eq(XSLTTemplateJson.class), eq("templates-json"))).thenReturn(templateJson1);
        // when
        XSLTTemplateJson result = templateService.getTemplateById(id);
        // then
        assertThat(result).isEqualTo(templateJson1);
        verify(mongoTemplate).findOne(eq(query), eq(XSLTTemplateJson.class), eq("templates-json"));
    }

    @Test
    void testGetTemplateByFilename() {
        // given
        String filename = "template.json";
        XSLTTemplateJson templateJson1 = new XSLTTemplateJson("123", "FileName", "Descr", "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:template match=\"/root\">...</xsl:template></xsl:stylesheet>");
        Query query = new Query(Criteria.where("filename").is(filename));
        when(mongoTemplate.find(eq(query), eq(XSLTTemplateJson.class))).thenReturn(Arrays.asList(templateJson1));
        // when
        XSLTTemplateJson result = templateService.getTemplateByFilename(filename);
        // then
        assertThat(result).isEqualTo(templateJson1);
        verify(mongoTemplate).find(eq(query), eq(XSLTTemplateJson.class));
    }

}