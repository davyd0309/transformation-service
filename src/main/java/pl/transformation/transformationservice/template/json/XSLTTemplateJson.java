package pl.transformation.transformationservice.template.json;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "templates-json")
public record XSLTTemplateJson(@Id String id, String filename, String description, String xsltContent) {
}
