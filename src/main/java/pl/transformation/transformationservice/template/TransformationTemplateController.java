package pl.transformation.transformationservice.template;

import com.mongodb.DBObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/template")
public class TransformationTemplateController {

    private final TransformationTemplateService transformationTemplateService;

    public TransformationTemplateController(TransformationTemplateService transformationTemplateService) {
        this.transformationTemplateService = transformationTemplateService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<XSLTTemplate> createTemplate(@RequestBody XSLTTemplate xsltTemplate) {
        return new ResponseEntity<>(transformationTemplateService.createTemplate(xsltTemplate), HttpStatus.CREATED);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<XSLTTemplate>> getAllTemplates() {
        return new ResponseEntity<>(transformationTemplateService.getAllTemplates(),
                HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<XSLTTemplate> getTemplateById(@PathVariable("id") String id) {
        return new ResponseEntity<>(transformationTemplateService.getTemplateById(id),
                HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTemplate(@RequestParam(value = "filename", required = false) String filename,
                                                 @RequestParam(value = "id", required = false) String id) {
        return filename!=null ?
                new ResponseEntity<>(transformationTemplateService.deleteTemplateByFileName(filename), HttpStatus.CREATED) :
                new ResponseEntity<>(transformationTemplateService.deleteTemplateById(id), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", produces = "application/xml")
    public ResponseEntity<String> replaceTemplateById(@PathVariable("id") String id, @RequestBody String xsltData) {
        return new ResponseEntity<>(transformationTemplateService.replaceTemplateById(id, xsltData),
                HttpStatus.CREATED);
    }
}
