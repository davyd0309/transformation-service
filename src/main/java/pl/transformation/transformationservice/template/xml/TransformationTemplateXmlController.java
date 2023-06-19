package pl.transformation.transformationservice.template.xml;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/template/xml")
public class TransformationTemplateXmlController {

    private final TransformationTemplateXmlService transformationTemplateXmlService;

    public TransformationTemplateXmlController(TransformationTemplateXmlService transformationTemplateXmlService) {
        this.transformationTemplateXmlService = transformationTemplateXmlService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    @Operation(
            summary = "Create a new template",
            description = "Example XML: <xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                    "    <xsl:output method=\"xml\" indent=\"yes\"/>\n" +
                    "    <xsl:param name=\"infoParam\" select=\"'\\''additional information saved'\\''\" />\n" +
                    "    <xsl:template match=\"/\">\n" +
                    "        <xsl:copy>\n" +
                    "            <xsl:apply-templates select=\"*\"/>\n" +
                    "        </xsl:copy>\n" +
                    "    </xsl:template>\n" +
                    "    <xsl:template match=\"*\">\n" +
                    "        <xsl:copy>\n" +
                    "            <xsl:copy-of select=\"@*\"/>\n" +
                    "            <xsl:apply-templates select=\"node()\"/>\n" +
                    "        </xsl:copy>\n" +
                    "    </xsl:template>\n" +
                    "    <xsl:template match=\"text()\">\n" +
                    "        <xsl:if test=\"normalize-space(.) != '\\'''\\''\">\n" +
                    "            <xsl:value-of select=\"normalize-space(.)\"/>\n" +
                    "        </xsl:if>\n" +
                    "    </xsl:template>\n" +
                    "    <xsl:template match=\"person\">\n" +
                    "        <xsl:copy>\n" +
                    "            <xsl:apply-templates select=\"@*|node()\"/>\n" +
                    "            <info><xsl:value-of select=\"normalize-space($infoParam)\"/></info>\n" +
                    "        </xsl:copy>\n" +
                    "    </xsl:template>\n" +
                    "</xsl:stylesheet>"
    )
    public ResponseEntity<XSLTTemplateXml> createTemplate(@org.springframework.web.bind.annotation.RequestBody String xsltTemplate) {
        return new ResponseEntity<>(transformationTemplateXmlService.createTemplate(xsltTemplate), HttpStatus.CREATED);
    }

    @GetMapping()
    @Operation(summary = "Get all templates")
    public ResponseEntity<List<XSLTTemplateXml>> getAllTemplates() {
        return new ResponseEntity<>(transformationTemplateXmlService.getAllTemplates(),
                HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get template by ID")
    public ResponseEntity<XSLTTemplateXml> getTemplateById(
            @PathVariable("id")
            @Parameter(description = "ID of the template to be obtained.", required = true)
            String id
    ) {
        XSLTTemplateXml templateById = transformationTemplateXmlService.getTemplateById(id);
        return Objects.isNull(templateById.getId()) ?
                new ResponseEntity<>(templateById, HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(templateById, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable("id") String id) {
        transformationTemplateXmlService.deleteTemplateById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<XSLTTemplateXml> replaceTemplateById(@PathVariable("id") String id, @RequestBody String xsltTemplate) {
        return new ResponseEntity<>(transformationTemplateXmlService.replaceTemplateById(id, xsltTemplate),
                HttpStatus.OK);
    }
}