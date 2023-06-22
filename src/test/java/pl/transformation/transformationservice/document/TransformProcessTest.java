package pl.transformation.transformationservice.document;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import pl.transformation.transformationservice.template.json.XSLTTemplateJson;
import pl.transformation.transformationservice.template.xml.XSLTTemplateXml;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class TransformProcessTest {

    @Mock
    private MongoTemplate mongoTemplate;

    private TransformProcess transformProcess;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transformProcess = new TransformProcess(mongoTemplate);
    }

    @Test
    void testTransformXml_XmlTemplate() {
        // given
        DocumentData documentData = new DocumentData("<root>Hello, World!</root>", "xml", false, "1");
        XSLTTemplateXml template = new XSLTTemplateXml("1", "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:template match=\"/root\">...</xsl:template></xsl:stylesheet>");
        when(mongoTemplate.findOne(any(Query.class), eq(org.bson.Document.class), eq("templates")))
                .thenReturn(new Document(Map.of(
                        "_id", new ObjectId("132423424324242423432344"),
                        "template", "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:template match=\"/root\">...</xsl:template></xsl:stylesheet>")));
        // when
        ByteArrayResource result = transformProcess.transformXml(documentData);
        // then
        assertThat(result).isNotNull();
        assertThat(result.contentLength()).isGreaterThan(0);
    }

    @Test
    void testTransformXml_JsonTemplate() {
        // given
        DocumentData documentData = new DocumentData("<root>Hello, World!</root>", "json", false, "1");
        XSLTTemplateJson template = new XSLTTemplateJson("1", "FileName", "Descr", "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:template match=\"/root\">...</xsl:template></xsl:stylesheet>");
        when(mongoTemplate.findOne(any(Query.class), eq(XSLTTemplateJson.class), eq("templates-json")))
                .thenReturn(template);
        // when
        ByteArrayResource result = transformProcess.transformXml(documentData);
        // then
        assertThat(result).isNotNull();
        assertThat(result.contentLength()).isGreaterThan(0);
    }

    @Test
    void transformXml_ShouldReturnNull_WhenTemplateNotFound() throws IOException, ParserConfigurationException, TransformerConfigurationException {
        // given
        String xmlData = "<data>Test XML</data>";
        String templateId = "nonExistingTemplateId";
        DocumentData documentData = new DocumentData(xmlData, "xml", true, templateId);

        when(mongoTemplate.findOne(any(Query.class), eq(org.bson.Document.class), eq("templates")))
                .thenReturn(null);
        // when
        ByteArrayResource transformedXml = transformProcess.transformXml(documentData);
        // then
        assertNull(transformedXml);
    }

    @Test
    void transformXml_ShouldReturnNull_WhenXmlParsingErrorOccurs() throws IOException, ParserConfigurationException, TransformerConfigurationException {
        // given
        String xmlData = "<data>Invalid XML"; // Invalid XML without closing tag
        String templateId = "templateXmlId";
        String templateContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">...</xsl:stylesheet>";
        DocumentData documentData = new DocumentData(xmlData, "xml", true, templateId);

        when(mongoTemplate.findOne(any(Query.class), eq(org.bson.Document.class), eq("templates")))
                .thenReturn(new org.bson.Document("_id", templateId).append("template", templateContent));
        // when
        ByteArrayResource transformedXml = transformProcess.transformXml(documentData);
        // then
        assertNull(transformedXml);
    }
}