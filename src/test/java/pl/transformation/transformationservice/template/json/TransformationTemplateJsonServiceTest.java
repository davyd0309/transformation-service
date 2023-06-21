package pl.transformation.transformationservice.template.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.transformation.transformationservice.template.TransformationTemplateRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransformationTemplateJsonServiceTest {

    @Mock
    private TransformationTemplateRepository<XSLTTemplateJson> repository;
    private TransformationTemplateJsonService service;

    @BeforeEach
    void setup() {
        service = new TransformationTemplateJsonService(repository);
    }

    @Test
    void testCreateTemplate() {
        // given
        XSLTTemplateJson template = new XSLTTemplateJson("1", "PersonalDataChange", "Template description", "<xsl:stylesheet>...</xsl:stylesheet>");
        when(repository.createTemplate(template)).thenReturn(template);
        // when
        XSLTTemplateJson result = service.createTemplate(template);
        // then
        verify(repository).createTemplate(template);
        assertEquals(template, result);
    }

    @Test
    void testGetAllTemplates() {
        // given
        List<XSLTTemplateJson> templates = Arrays.asList(
                new XSLTTemplateJson("1", "Template1", "Description1", "<xsl:stylesheet>...</xsl:stylesheet>"),
                new XSLTTemplateJson("2", "Template2", "Description2", "<xsl:stylesheet>...</xsl:stylesheet>")
        );
        when(repository.getAllTemplates()).thenReturn(templates);
        // when
        List<XSLTTemplateJson> result = service.getAllTemplates();
        // then
        verify(repository).getAllTemplates();
        assertEquals(templates, result);
    }

    @Test
    void testDeleteTemplateById() {
        // given
        String id = "1";
        // when
        service.deleteTemplateById(id);
        // then
        verify(repository).deleteTemplateById(id);
    }

    @Test
    void testDeleteTemplateByFileName() {
        // given
        String filename = "PersonalDataChange";
        // when
        service.deleteTemplateByFileName(filename);
        // then
        verify(repository).deleteTemplateByFileName(filename);
    }

    @Test
    void testReplaceTemplateById() {
        // given
        String id = "1";
        XSLTTemplateJson template = new XSLTTemplateJson("1", "PersonalDataChange", "Template description", "<xsl:stylesheet>...</xsl:stylesheet>");
        when(repository.replaceTemplateById(id, template)).thenReturn(template);
        // when
        XSLTTemplateJson result = service.replaceTemplateById(id, template);
        // then
        verify(repository).replaceTemplateById(id, template);
        assertEquals(template, result);
    }

    @Test
    void testGetTemplateById() {
        // given
        String id = "1";
        XSLTTemplateJson template = new XSLTTemplateJson("1", "PersonalDataChange", "Template description", "<xsl:stylesheet>...</xsl:stylesheet>");
        when(repository.getTemplateById(id)).thenReturn(template);
        // when
        XSLTTemplateJson result = service.getTemplateById(id);
        // then
        verify(repository).getTemplateById(id);
        assertEquals(template, result);
    }

    @Test
    void testGetTemplateByFilename() {
        // given
        String filename = "PersonalDataChange";
        XSLTTemplateJson template = new XSLTTemplateJson("1", "PersonalDataChange", "Template description", "<xsl:stylesheet>...</xsl:stylesheet>");
        when(repository.getTemplateByFilename(filename)).thenReturn(template);
        // when
        XSLTTemplateJson result = service.getTemplateByFilename(filename);
        // then
        verify(repository).getTemplateByFilename(filename);
        assertEquals(template, result);
    }

}