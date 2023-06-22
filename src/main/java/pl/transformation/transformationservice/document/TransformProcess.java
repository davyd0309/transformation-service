package pl.transformation.transformationservice.document;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import pl.transformation.transformationservice.template.json.XSLTTemplateJson;
import pl.transformation.transformationservice.template.xml.XSLTTemplateXml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Optional;

public class TransformProcess {

    private static final Logger log = LoggerFactory.getLogger(TransformProcess.class);

    private final MongoTemplate mongoTemplate;

    public TransformProcess(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public ByteArrayResource transformXml(DocumentData documentData) {
        try {
            String template = getTemplate(documentData);
            Document xmlDoc = createXmlBasedOnEntryFile(documentData);
            Transformer transformer = createTransformer(template);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(new DOMSource(xmlDoc), result);
            String transformedXML = writer.toString();
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                outputStream.write(transformedXML.getBytes());
                return new ByteArrayResource(outputStream.toByteArray());
            } catch (IOException e) {
                log.error("An error occurred during IO operation: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            log.error("An error occurred during XML transformation: " + e.getMessage(), e);
        }
        return null;
    }

    private String getTemplate(DocumentData documentData) {
        String template = Strings.EMPTY;
        if (documentData.templateSavedType().equals("json")) { // TODO change to Enum
            XSLTTemplateJson templateByIdJson = getTemplateByIdJson(documentData.templateId());
            template = templateByIdJson.xsltContent();
        }
        if (documentData.templateSavedType().equals("xml")) { // TODO change to Enum
            XSLTTemplateXml templateByIdXml = getTemplateByIdXml(documentData.templateId());
            template = templateByIdXml.getTemplate();
        }
        return template;
    }

    private Transformer createTransformer(String template) throws TransformerConfigurationException {
        StreamSource xsltStreamSource =
                new StreamSource(new StringReader(Optional.ofNullable(template)
                        .orElseThrow(() -> new IllegalArgumentException("Template not exist"))));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        //transformer.setParameter("paramName", "wartośćParametru");
        return transformerFactory.newTransformer(xsltStreamSource);
    }

    private Document createXmlBasedOnEntryFile(DocumentData documentData) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource inputSource = new InputSource(new StringReader(documentData.xmlData()));
        return builder.parse(inputSource);
    }

    private XSLTTemplateXml getTemplateByIdXml(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return Optional.ofNullable(mongoTemplate.findOne(query, org.bson.Document.class, "templates"))
                .map(document -> new XSLTTemplateXml(document.getObjectId("_id").toHexString(), document.getString("template")))
                .orElse(new XSLTTemplateXml(Strings.EMPTY));
    }

    private XSLTTemplateJson getTemplateByIdJson(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, XSLTTemplateJson.class, "templates-json");
    }
}
