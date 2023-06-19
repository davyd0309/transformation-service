package pl.transformation.transformationservice.template.xml;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Operation(summary = "Create a new template")
    public ResponseEntity<XSLTTemplateXml> createTemplate(@RequestBody String xsltTemplate) {
        return new ResponseEntity<>(transformationTemplateXmlService.createTemplate(xsltTemplate), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<XSLTTemplateXml>> getAllTemplates() {
        return new ResponseEntity<>(transformationTemplateXmlService.getAllTemplates(),
                HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<XSLTTemplateXml> getTemplateById(@PathVariable("id") String id) {
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