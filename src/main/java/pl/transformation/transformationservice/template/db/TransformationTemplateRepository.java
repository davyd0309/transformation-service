package pl.transformation.transformationservice.template.db;

import java.util.List;

public interface TransformationTemplateRepository<T> {

    T createTemplate(T template);

    List<T> getAllTemplates();

    Long deleteTemplateById(String id);

    Long deleteTemplateByFileName(String filename);

    T replaceTemplateById(String id, T template);

    T getTemplateById(String id);

    T getTemplateByFilename(String filename);
}
