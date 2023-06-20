package pl.transformation.transformationservice.document;

import org.bson.Document;
import org.bson.types.ObjectId;
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

import java.util.Map;
import java.util.concurrent.ExecutorService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;


class TransformationDocumentServiceTest {

    private final MongoTemplate mongoTemplate = Mockito.mock(MongoTemplate.class);
    private final ExecutorService executorService = Mockito.mock(ExecutorService.class);
    private final TransformationDocumentService transformationDocumentService = new TransformationDocumentService(mongoTemplate, executorService);

    @Test
    void testTransformXml_XmlTemplate() {
        // given
        DocumentData documentData = new DocumentData("<root>Hello, World!</root>", "xml", false, false, "1");
        XSLTTemplateXml template = new XSLTTemplateXml("1", "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:template match=\"/root\">...</xsl:template></xsl:stylesheet>");
        when(mongoTemplate.findOne(any(Query.class), eq(org.bson.Document.class), eq("templates")))
                .thenReturn(new Document(Map.of(
                        "_id", new ObjectId("132423424324242423432344"),
                        "template", "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:template match=\"/root\">...</xsl:template></xsl:stylesheet>")));
        // when
        ByteArrayResource result = transformationDocumentService.transformXml(documentData).block();
        // then
        assertThat(result).isNotNull();
        assertThat(result.contentLength()).isGreaterThan(0);
    }

    @Test
    void testTransformXml_JsonTemplate() {
        // given
        DocumentData documentData = new DocumentData("<root>Hello, World!</root>", "json", false, false, "1");
        XSLTTemplateJson template = new XSLTTemplateJson("1", "FileName", "Descr", "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:template match=\"/root\">...</xsl:template></xsl:stylesheet>");
        when(mongoTemplate.findOne(any(Query.class), eq(XSLTTemplateJson.class), eq("templates-json")))
                .thenReturn(template);
        // when
        ByteArrayResource result = transformationDocumentService.transformXml(documentData).block();
        // then
        assertThat(result).isNotNull();
        assertThat(result.contentLength()).isGreaterThan(0);
    }

}