package pl.transformation.transformationservice.template.xml;

import pl.transformation.transformationservice.template.TransformationTemplateRepository;

import java.util.List;

public class TransformationTemplateXmlService {

    private final TransformationTemplateRepository<XSLTTemplateXml> transformationTemplateRepository;

    public TransformationTemplateXmlService(TransformationTemplateRepository<XSLTTemplateXml> transformationTemplateRepository) {
        this.transformationTemplateRepository = transformationTemplateRepository;
    }

    public XSLTTemplateXml createTemplate(String xsltTemplate) {
        XSLTTemplateXml template = new XSLTTemplateXml(xsltTemplate);
        return transformationTemplateRepository.createTemplate(template);
    }

    public List<XSLTTemplateXml> getAllTemplates() {
        return transformationTemplateRepository.getAllTemplates();
    }

    public void deleteTemplateById(String id) {
        transformationTemplateRepository.deleteTemplateById(id);
    }

    public XSLTTemplateXml replaceTemplateById(String id, String xsltTemplate) {
        XSLTTemplateXml template = new XSLTTemplateXml(id, xsltTemplate);
        return transformationTemplateRepository.replaceTemplateById(id, template);
    }

    public XSLTTemplateXml getTemplateById(String id) {
        return transformationTemplateRepository.getTemplateById(id);
    }
}
