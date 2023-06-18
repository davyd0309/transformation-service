package pl.transformation.transformationservice.template.json;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "templates-json")
record XSLTTemplate(@Id String id,String filename, String description, String xsltContent) {
}
