package pl.transformation.transformationservice.document;

import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.transformation.transformationservice.template.json.XSLTTemplateJson;
import pl.transformation.transformationservice.template.xml.XSLTTemplateXml;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;


class TransformationDocumentServiceTest {

    private final MongoTemplate mongoTemplate = Mockito.mock(MongoTemplate.class);
    private final TransformationDocumentService transformationDocumentService = new TransformationDocumentService(mongoTemplate);

    @Test
    void testTransformXml() throws Exception {
        // Given
        DocumentData documentData = new DocumentData("<root>Hello, World!</root>","xml",false,false,"1");
        XSLTTemplateXml template = new XSLTTemplateXml("1","<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:template match=\"/root\">...</xsl:template></xsl:stylesheet>");

        when(mongoTemplate.findOne(any(Query.class), eq(XSLTTemplateXml.class), eq("templates")))
                .thenReturn(template);

        // When
        ByteArrayResource result = transformationDocumentService.transformXml(documentData);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.contentLength()).isGreaterThan(0);
        // Additional assertions for the transformed XML content
    }

//    @Test
//    void testTransformXml_JsonTemplate() throws Exception {
//        // Given
//        DocumentData documentData = new DocumentData();
//        documentData.setTemplateSavedType("json");
//        documentData.setTemplateId("1");
//        documentData.setXmlData("<root>Hello, World!</root>");
//
//        XSLTTemplateJson template = new XSLTTemplateJson();
//        template.setId("1");
//        template.setXsltContent("<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:template match=\"/root\">...</xsl:template></xsl:stylesheet>");
//
//        when(mongoTemplate.findOne(any(Query.class), eq(XSLTTemplateJson.class), eq("templates-json")))
//                .thenReturn(template);
//
//        // When
//        ByteArrayResource result = transformationDocumentService.transformXml(documentData);
//
//        // Then
//        assertThat(result).isNotNull();
//        assertThat(result.contentLength()).isGreaterThan(0);
//        // Additional assertions for the transformed XML content
//    }
//
//    @Test
//    void testTransformXml_TemplateNotFound() throws Exception {
//        // Given
//        DocumentData documentData = new DocumentData();
//        documentData.setTemplateSavedType("xml");
//        documentData.setTemplateId("1");
//        documentData.setXmlData("<root>Hello, World!</root>");
//
//        when(mongoTemplate.findOne(any(Query.class), eq(XSLTTemplateXml.class), eq("templates")))
//                .thenReturn(null);
//
//        // When
//        ByteArrayResource result = transformationDocumentService.transformXml(documentData);
//
//        // Then
//        assertThat(result).isNull();
//    }
}