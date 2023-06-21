package pl.transformation.transformationservice.template.json;

import pl.transformation.transformationservice.template.db.TransformationTemplateRepository;

import java.util.List;

public class TransformationTemplateJsonService {

    private final TransformationTemplateRepository<XSLTTemplateJson> transformationTemplateRepository;

    public TransformationTemplateJsonService(TransformationTemplateRepository<XSLTTemplateJson> transformationTemplateRepository) {
        this.transformationTemplateRepository = transformationTemplateRepository;
    }

    public XSLTTemplateJson createTemplate(XSLTTemplateJson xsltTemplateJson) {
        return transformationTemplateRepository.createTemplate(xsltTemplateJson);
    }

    public List<XSLTTemplateJson> getAllTemplates() {
        return transformationTemplateRepository.getAllTemplates();
    }

    public Long deleteTemplateById(String id) {
        return transformationTemplateRepository.deleteTemplateById(id);
    }

    public Long deleteTemplateByFileName(String filename) {
        return transformationTemplateRepository.deleteTemplateByFileName(filename);
    }

    public XSLTTemplateJson replaceTemplateById(String id, XSLTTemplateJson xsltTemplateJson) {
        return transformationTemplateRepository.replaceTemplateById(id, xsltTemplateJson);
    }

    public XSLTTemplateJson getTemplateById(String id) {
        return transformationTemplateRepository.getTemplateById(id);
    }

    public XSLTTemplateJson getTemplateByFilename(String filename) {
        return transformationTemplateRepository.getTemplateByFilename(filename);
    }
}
