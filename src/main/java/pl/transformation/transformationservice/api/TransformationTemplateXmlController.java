package pl.transformation.transformationservice.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.transformation.transformationservice.template.xml.TransformationTemplateXmlService;
import pl.transformation.transformationservice.template.xml.XSLTTemplateXml;

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
            description = "Please copy the contents of the file 'body-for-create-template-xml.xslt' to the body of the request. File is in resources->body."
    )
    public ResponseEntity<XSLTTemplateXml> createTemplate(@RequestBody String xsltTemplate) {
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

    @Operation(summary = "Delete a template by id")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteTemplate(
            @Parameter(description = "ID of the template to be deleted", required = true)
            @PathVariable("id") String id
    ) {
        transformationTemplateXmlService.deleteTemplateById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Replace a template by its id",
            description = "Please copy the contents of the file 'body-for-update-template-xml.xslt' to the body of the request. File is in resources->body.")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<XSLTTemplateXml> replaceTemplateById(
            @Parameter(description = "ID of the template to be replaced", required = true)
            @PathVariable("id") String id,
            @Parameter(description = "New XSLT template to replace the old one", required = true)
            @RequestBody String xsltTemplate
    ) {
        return new ResponseEntity<>(transformationTemplateXmlService.replaceTemplateById(id, xsltTemplate),
                HttpStatus.OK);
    }
}