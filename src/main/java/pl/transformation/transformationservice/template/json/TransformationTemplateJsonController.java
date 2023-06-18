package pl.transformation.transformationservice.template.json;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/template/json")
public class TransformationTemplateJsonController {

    private final TransformationTemplateJsonService transformationTemplateJsonService;

    public TransformationTemplateJsonController(TransformationTemplateJsonService transformationTemplateJsonService) {
        this.transformationTemplateJsonService = transformationTemplateJsonService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<XSLTTemplate> createTemplate(@RequestBody XSLTTemplate xsltTemplate) {
        return new ResponseEntity<>(transformationTemplateJsonService.createTemplate(xsltTemplate), HttpStatus.CREATED);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<XSLTTemplate>> getAllTemplates() {
        return new ResponseEntity<>(transformationTemplateJsonService.getAllTemplates(),
                HttpStatus.OK);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<XSLTTemplate> getTemplateBy(@RequestParam(value = "filename", required = false) String filename,
                                                      @RequestParam(value = "id", required = false) String id) {
        if (filename != null) {
            XSLTTemplate templateByFilename = transformationTemplateJsonService.getTemplateByFilename(filename);
            return Objects.isNull(templateByFilename) ?
                    new ResponseEntity<>(templateByFilename, HttpStatus.NO_CONTENT) :
                    new ResponseEntity<>(templateByFilename, HttpStatus.OK);
        } else if (id != null) {
            XSLTTemplate templateById = transformationTemplateJsonService.getTemplateById(id);
            return Objects.isNull(templateById.id()) ?
                    new ResponseEntity<>(templateById, HttpStatus.NO_CONTENT) :
                    new ResponseEntity<>(templateById, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteTemplate(@RequestParam(value = "filename", required = false) String filename,
                                               @RequestParam(value = "id", required = false) String id) {
        if (filename != null) {
            transformationTemplateJsonService.deleteTemplateByFileName(filename);
        } else if (id != null) {
            transformationTemplateJsonService.deleteTemplateById(id);
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<XSLTTemplate> replaceTemplateById(@PathVariable("id") String id, @RequestBody XSLTTemplate xsltTemplate) {
        return new ResponseEntity<>(transformationTemplateJsonService.replaceTemplateById(id, xsltTemplate),
                HttpStatus.OK);
    }
}
