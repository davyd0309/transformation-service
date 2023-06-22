package pl.transformation.transformationservice.document;

public record DocumentData(String xmlData,
                           String templateSavedType,
                           boolean logDocument,
                           String templateId) {
}
