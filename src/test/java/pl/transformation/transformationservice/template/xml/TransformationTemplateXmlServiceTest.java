package pl.transformation.transformationservice.template.xml;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.transformation.transformationservice.template.db.TransformationTemplateRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TransformationTemplateXmlServiceTest {

    @Mock
    private TransformationTemplateRepository<XSLTTemplateXml> repository;
    private TransformationTemplateXmlService service;

    @BeforeEach
    void setup() {
        service = new TransformationTemplateXmlService(repository);
    }

    @Test
    void testGetAllTemplates() {
        // given
        List<XSLTTemplateXml> templates = Arrays.asList(
                new XSLTTemplateXml("1", "<xsl:stylesheet>...</xsl:stylesheet>"),
                new XSLTTemplateXml("2", "<xsl:stylesheet>...</xsl:stylesheet>")
        );
        when(repository.getAllTemplates()).thenReturn(templates);
        // when
        List<XSLTTemplateXml> result = service.getAllTemplates();
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
    void testGetTemplateById() {
        // given
        String id = "1";
        XSLTTemplateXml template = new XSLTTemplateXml(id, "<xsl:stylesheet>...</xsl:stylesheet>");
        when(repository.getTemplateById(id)).thenReturn(template);
        // when
        XSLTTemplateXml result = service.getTemplateById(id);
        // then
        verify(repository).getTemplateById(id);
        assertEquals(template, result);
    }
}