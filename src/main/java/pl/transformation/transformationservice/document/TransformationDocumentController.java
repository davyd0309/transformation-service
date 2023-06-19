package pl.transformation.transformationservice.document;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/api/template/generate")
public class TransformationDocumentController {

    private final TransformationDocumentService transformationDocumentService;

    public TransformationDocumentController(TransformationDocumentService transformationDocumentService) {
        this.transformationDocumentService = transformationDocumentService;
    }

    @GetMapping(value = "/{templateId}", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
    public ResponseEntity<ByteArrayResource> generateDocument(@PathVariable("templateId") String templateId,
                                                              @RequestBody String xmlData,
                                                              @RequestParam(value = "templateSavedType") String templateSavedType,
                                                              @RequestParam(value = "logDocument") boolean logDocument,
                                                              @RequestParam(value = "asynchronous") boolean asynchronous
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setContentDispositionFormData("attachment", "transformedDocument.xml");
        return ResponseEntity.ok()
                .headers(headers)
                .body(transformationDocumentService.transformXml
                        (new DocumentData(xmlData, templateSavedType, logDocument, asynchronous, templateId)));
    }
}
