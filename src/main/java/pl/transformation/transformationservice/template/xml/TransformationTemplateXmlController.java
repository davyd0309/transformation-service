package pl.transformation.transformationservice.template.xml;

import org.bson.Document;
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
    public ResponseEntity<DocumentDto> createTemplate(@RequestBody String xsltTemplate) {
        return new ResponseEntity<>(transformationTemplateXmlService.createTemplate(xsltTemplate), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<DocumentDto>> getAllTemplates() {
        return new ResponseEntity<>(transformationTemplateXmlService.getAllTemplates(),
                HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DocumentDto> getTemplateById(@PathVariable("id") String id) {
        DocumentDto templateById = transformationTemplateXmlService.getTemplateById(id);
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
    public ResponseEntity<DocumentDto> replaceTemplateById(@PathVariable("id") String id, @RequestBody String xsltTemplate) {
        return new ResponseEntity<>(transformationTemplateXmlService.replaceTemplateById(id, xsltTemplate),
                HttpStatus.OK);
    }
}