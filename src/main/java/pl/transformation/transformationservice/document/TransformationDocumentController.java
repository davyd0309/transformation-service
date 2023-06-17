package pl.transformation.transformationservice.document;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/api/template/generate")
public class TransformationDocumentController {

    private final TransformationDocumentService transformationDocumentService;

    public TransformationDocumentController(TransformationDocumentService transformationDocumentService) {
        this.transformationDocumentService = transformationDocumentService;
    }

    @GetMapping(consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
    public ResponseEntity<String> generateDocument(@RequestBody String xmlData) {
        return new ResponseEntity<>(transformationDocumentService.transformXml(xmlData), HttpStatus.CREATED);
    }

}
