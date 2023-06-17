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

    @PostMapping(consumes = "application/xml")
    public ResponseEntity<String> createTemplate(@RequestBody String xsltData) {
        return new ResponseEntity<>(transformationTemplateService.createTemplate(xsltData), HttpStatus.CREATED);
    }

    @GetMapping(produces = "application/xml")
    public ResponseEntity<List<DBObject>> getAllTemplates() {
        return new ResponseEntity<>(transformationTemplateService.getAllTemplates(), HttpStatus.CREATED);
    }


}
