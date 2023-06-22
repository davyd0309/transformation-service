package pl.transformation.transformationservice.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.transformation.transformationservice.document.DocumentData;
import pl.transformation.transformationservice.document.TransformationDocumentAsyncService;
import pl.transformation.transformationservice.document.TransformationDocumentSyncService;

import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RestController
@RequestMapping("/api/template")
public class TransformationDocumentController {

    private final TransformationDocumentSyncService transformationDocumentSyncService;
    private final TransformationDocumentAsyncService transformationDocumentAsyncService;

    public TransformationDocumentController(TransformationDocumentSyncService transformationDocumentSyncService,
                                            TransformationDocumentAsyncService transformationDocumentAsyncService) {
        this.transformationDocumentSyncService = transformationDocumentSyncService;
        this.transformationDocumentAsyncService = transformationDocumentAsyncService;
    }

    @Operation(
            summary = "Synchronous xml document generation",
            description = """ 
                     Example XML - PLEASE PASTE IN BODY : 
                     
                    &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
                    &lt;xmlData&gt;
                        &lt;person&gt;
                            &lt;name&gt;Jan Serce&lt;/name&gt;
                            &lt;age&gt;30&lt;/age&gt;
                            &lt;info&gt;additional information saved xml&lt;/info&gt;
                        &lt;/person&gt;
                    &lt;/xmlData&gt;
                    """,
            parameters = {
                    @Parameter(
                            in = ParameterIn.PATH,
                            name = "templateId",
                            required = true,
                            description = "ID of the template"
                    ),
                    @Parameter(
                            name = "templateSavedType",
                            required = true,
                            description = "Type of the saved template (xml or json)"
                    ),
                    @Parameter(
                            name = "logDocument",
                            required = true,
                            description = "Log document flag",
                            example = "false"
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully generated XML document"),
            }
    )
    @PostMapping(value = "/{templateId}/generate", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
    public ResponseEntity<ByteArrayResource> generateDocument(@PathVariable("templateId") String templateId,
                                                              @org.springframework.web.bind.annotation.RequestBody String xmlData,
                                                              @RequestParam(value = "templateSavedType") String templateSavedType,
                                                              @RequestParam(value = "logDocument") boolean logDocument
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setContentDispositionFormData("attachment", "transformedDocument.xml");
        return ResponseEntity.ok()
                .headers(headers)
                .body(transformationDocumentSyncService.transformXml(new DocumentData(xmlData, templateSavedType, logDocument, templateId)));
    }

    @Operation(
            summary = "Asynchronous xml document generation",
            description = """ 
                     Example XML - PLEASE PASTE IN BODY : 
                     
                    &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
                    &lt;xmlData&gt;
                        &lt;person&gt;
                            &lt;name&gt;Jan Serce&lt;/name&gt;
                            &lt;age&gt;30&lt;/age&gt;
                            &lt;info&gt;additional information saved xml&lt;/info&gt;
                        &lt;/person&gt;
                    &lt;/xmlData&gt;
                    """,
            parameters = {
                    @Parameter(
                            in = ParameterIn.PATH,
                            name = "templateId",
                            required = true,
                            description = "ID of the template"
                    ),
                    @Parameter(
                            name = "templateSavedType",
                            required = true,
                            description = "Type of the saved template (xml or json)"
                    ),
                    @Parameter(
                            name = "logDocument",
                            required = true,
                            description = "Log document flag",
                            example = "false"
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully generated XML document"),
            }
    )

    @PostMapping(value = "/{templateId}/generate/async", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
    public ResponseEntity<Void> generateDocumentAsync(@PathVariable("templateId") String templateId,
                                                      @org.springframework.web.bind.annotation.RequestBody String xmlData,
                                                      @RequestParam(value = "templateSavedType") String templateSavedType,
                                                      @RequestParam(value = "logDocument") boolean logDocument
    ) {
        transformationDocumentAsyncService.transformXml(new DocumentData(xmlData, templateSavedType, logDocument, templateId));
        return ResponseEntity.ok().build();
    }
}
