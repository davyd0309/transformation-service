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
    public ResponseEntity<XSLTTemplateJson> createTemplate(@RequestBody XSLTTemplateJson xsltTemplateJson) {
        return new ResponseEntity<>(transformationTemplateJsonService.createTemplate(xsltTemplateJson), HttpStatus.CREATED);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<XSLTTemplateJson>> getAllTemplates() {
        return new ResponseEntity<>(transformationTemplateJsonService.getAllTemplates(),
                HttpStatus.OK);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<XSLTTemplateJson> getTemplateBy(@RequestParam(value = "filename", required = false) String filename,
                                                          @RequestParam(value = "id", required = false) String id) {
        if (filename != null) {
            XSLTTemplateJson templateByFilename = transformationTemplateJsonService.getTemplateByFilename(filename);
            return Objects.isNull(templateByFilename) ?
                    new ResponseEntity<>(templateByFilename, HttpStatus.NO_CONTENT) :
                    new ResponseEntity<>(templateByFilename, HttpStatus.OK);
        } else if (id != null) {
            XSLTTemplateJson templateById = transformationTemplateJsonService.getTemplateById(id);
            return Objects.isNull(templateById) ?
                    new ResponseEntity<>(templateById, HttpStatus.NO_CONTENT) :
                    new ResponseEntity<>(templateById, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> deleteTemplate(@RequestParam(value = "filename", required = false) String filename,
                                               @RequestParam(value = "id", required = false) String id) {
        if (filename != null) {
            return new ResponseEntity<>(transformationTemplateJsonService.deleteTemplateByFileName(filename), HttpStatus.NO_CONTENT);
        } else if (id != null) {
            return new ResponseEntity<>(transformationTemplateJsonService.deleteTemplateById(id),HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<XSLTTemplateJson> replaceTemplateById(@PathVariable("id") String id, @RequestBody XSLTTemplateJson xsltTemplateJson) {
        return new ResponseEntity<>(transformationTemplateJsonService.replaceTemplateById(id, xsltTemplateJson),
                HttpStatus.OK);
    }
}
