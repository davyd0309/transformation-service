package pl.transformation.transformationservice.template;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "templates")
public record XSLTTemplate(String filename, String description, String xsltContent) {
}
