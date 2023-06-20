package pl.transformation.transformationservice.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.transformation.transformationservice.template.json.TransformationTemplateJsonService;
import pl.transformation.transformationservice.template.json.XSLTTemplateJson;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/template/json")
public class TransformationTemplateJsonController {

    private final TransformationTemplateJsonService transformationTemplateJsonService;

    public TransformationTemplateJsonController(TransformationTemplateJsonService transformationTemplateJsonService) {
        this.transformationTemplateJsonService = transformationTemplateJsonService;
    }

    @Operation(
            summary = "Create a new XSLT template",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = XSLTTemplateJson.class),
                            examples = {
                                    @ExampleObject(
                                            name = "example request",
                                            value = """
                                                    {
                                                      "filename": "PersonalDataChange",
                                                      "description": "Template use for add information about user",
                                                      "xsltContent": "<xsl:stylesheet version=\\"1.0\\" xmlns:xsl=\\"http://www.w3.org/1999/XSL/Transform\\">\\r\\n    <xsl:output method=\\"xml\\" indent=\\"yes\\"/>\\r\\n    <xsl:param name=\\"infoParam\\" select=\\"'additional information saved json'\\" />\\r\\n    <xsl:template match=\\"/\\">\\r\\n        <xsl:copy>\\r\\n            <xsl:apply-templates select=\\"*\\"/>\\r\\n        </xsl:copy>\\r\\n    </xsl:template>\\r\\n    <xsl:template match=\\"*\\">\\r\\n        <xsl:copy>\\r\\n            <xsl:copy-of select=\\"@*\\"/>\\r\\n            <xsl:apply-templates select=\\"node()\\"/>\\r\\n        </xsl:copy>\\r\\n    </xsl:template>\\r\\n    <xsl:template match=\\"text()\\">\\r\\n        <xsl:if test=\\"normalize-space(.) != ''\\">\\r\\n            <xsl:value-of select=\\"normalize-space(.)\\"/>\\r\\n        </xsl:if>\\r\\n    </xsl:template>\\r\\n    <xsl:template match=\\"person\\">\\r\\n        <xsl:copy>\\r\\n            <xsl:apply-templates select=\\"@*|node()\\"/>\\r\\n            <info><xsl:value-of select=\\"normalize-space($infoParam)\\"/></info>\\r\\n        </xsl:copy>\\r\\n    </xsl:template>\\r\\n</xsl:stylesheet>"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<XSLTTemplateJson> createTemplate(@org.springframework.web.bind.annotation.RequestBody XSLTTemplateJson xsltTemplateJson) {
        return new ResponseEntity<>(transformationTemplateJsonService.createTemplate(xsltTemplateJson), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all templates", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = XSLTTemplateJson.class))))})
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<XSLTTemplateJson>> getAllTemplates() {
        return new ResponseEntity<>(transformationTemplateJsonService.getAllTemplates(), HttpStatus.OK);
    }


    @Operation(
            summary = "Get XSLT template by filename or id",
            parameters = {
                    @Parameter(
                            name = "filename",
                            description = "Filename of the XSLT template"
                    ),
                    @Parameter(
                            name = "id",
                            description = "ID of the XSLT template"
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved template"),
                    @ApiResponse(responseCode = "204", description = "No content found"),
                    @ApiResponse(responseCode = "400", description = "Bad Request - filename or id is not provided")
            }
    )
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

    @Operation(
            summary = "Delete XSLT template by filename or id",
            parameters = {
                    @Parameter(
                            name = "filename",
                            description = "Filename of the XSLT template"
                    ),
                    @Parameter(
                            name = "id",
                            description = "ID of the XSLT template"
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Successfully deleted template or no content found"),
                    @ApiResponse(responseCode = "400", description = "Bad Request - filename or id is not provided")
            }
    )
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> deleteTemplate(@RequestParam(value = "filename", required = false) String filename,
                                               @RequestParam(value = "id", required = false) String id) {
        if (filename != null) {
            return new ResponseEntity<>(transformationTemplateJsonService.deleteTemplateByFileName(filename), HttpStatus.NO_CONTENT);
        } else if (id != null) {
            return new ResponseEntity<>(transformationTemplateJsonService.deleteTemplateById(id), HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Replace XSLT template by id",
            parameters = {
                    @Parameter(
                            in = ParameterIn.PATH,
                            name = "id",
                            required = true,
                            description = "ID of the XSLT template to replace"
                    )
            },
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = XSLTTemplateJson.class),
                            examples = {
                                    @ExampleObject(
                                            name = "example request",
                                            value = "{\"filename\": \"PersonalDataChange2\", \"description\": \"Template use for add phone user\", \"xsltContent\": \"<xsl:stylesheet version=\\\"1.0\\\" xmlns:xsl=\\\"http://www.w3.org/1999/XSL/Transform\\\">\\r\\n <xsl:output method=\\\"xml\\\" indent=\\\"yes\\\"/>\\r\\n <xsl:param name=\\\"infoParam\\\" select=\\\"'664 533 111'\\\" />\\r\\n <xsl:template match=\\\"/\\\">\\r\\n <xsl:copy>\\r\\n <xsl:apply-templates select=\\\"\\\"/>\\r\\n </xsl:copy>\\r\\n </xsl:template>\\r\\n <xsl:template match=\\\"\\\">\\r\\n <xsl:copy>\\r\\n <xsl:copy-of select=\\\"@\\\"/>\\r\\n <xsl:apply-templates select=\\\"node()\\\"/>\\r\\n </xsl:copy>\\r\\n </xsl:template>\\r\\n <xsl:template match=\\\"text()\\\">\\r\\n <xsl:if test=\\\"normalize-space(.) != ''\\\">\\r\\n <xsl:value-of select=\\\"normalize-space(.)\\\"/>\\r\\n </xsl:if>\\r\\n </xsl:template>\\r\\n <xsl:template match=\\\"person\\\">\\r\\n <xsl:copy>\\r\\n <xsl:apply-templates select=\\\"@|node()\\\"/>\\r\\n <xsl:value-of select=\\\"normalize-space($infoParam)\\\"/>\\r\\n </xsl:copy>\\r\\n </xsl:template>\\r\\n</xsl:stylesheet>\"}"
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully replaced template"),
            }
    )
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<XSLTTemplateJson> replaceTemplateById(@PathVariable("id") String id, @org.springframework.web.bind.annotation.RequestBody XSLTTemplateJson xsltTemplateJson) {
        return new ResponseEntity<>(transformationTemplateJsonService.replaceTemplateById(id, xsltTemplateJson),
                HttpStatus.OK);
    }
}
