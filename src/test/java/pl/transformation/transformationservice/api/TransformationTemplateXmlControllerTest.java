package pl.transformation.transformationservice.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.transformation.transformationservice.template.xml.TransformationTemplateXmlService;
import pl.transformation.transformationservice.template.xml.XSLTTemplateXml;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TransformationTemplateXmlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransformationTemplateXmlService transformationTemplateXmlService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new TransformationTemplateXmlController(transformationTemplateXmlService))
                .build();
    }

    @Test
    public void testCreateTemplate() throws Exception {
        String xsltTemplate = "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:template match=\"/\">...</xsl:template></xsl:stylesheet>";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/template/xml")
                        .content(xsltTemplate)
                        .contentType(MediaType.APPLICATION_XML))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.template").value(xsltTemplate))
                .andReturn();
    }

    @Test
    public void testGetAllTemplates() throws Exception {
        String xsltTemplate1 = "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:template match=\"/\">...</xsl:template></xsl:stylesheet>";
        String xsltTemplate2 = "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:template match=\"/\">...</xsl:template></xsl:stylesheet>";
        transformationTemplateXmlService.createTemplate(xsltTemplate1);
        transformationTemplateXmlService.createTemplate(xsltTemplate2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/template/xml"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].template").value(xsltTemplate1))
                .andExpect(jsonPath("$[1].template").value(xsltTemplate2));
    }

    @Test
    public void testGetTemplateById() throws Exception {
        String xsltTemplate = "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:template match=\"/\">...</xsl:template></xsl:stylesheet>";
        XSLTTemplateXml template = transformationTemplateXmlService.createTemplate(xsltTemplate);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/template/xml/{id}", template.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.template").value(template.getTemplate()));
    }

    @Test
    public void testGetTemplateById_NotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/template/xml/{id}", "nonexistent-id"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteTemplate() throws Exception {
        String xsltTemplate = "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:template match=\"/\">...</xsl:template></xsl:stylesheet>";
        XSLTTemplateXml template = transformationTemplateXmlService.createTemplate(xsltTemplate);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/template/xml/{id}", template.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/template/xml/{id}", template.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testReplaceTemplateById() throws Exception {
        String xsltTemplate = "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:template match=\"/\">...</xsl:template></xsl:stylesheet>";
        XSLTTemplateXml template = transformationTemplateXmlService.createTemplate(xsltTemplate);
        String updatedTemplate = "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:template match=\"/\">Updated template</xsl:template></xsl:stylesheet>";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/template/xml/{id}", template.getId())
                        .content(updatedTemplate)
                        .contentType(MediaType.APPLICATION_XML))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(template.getId()))
                .andExpect(jsonPath("$.template").value(updatedTemplate))
                .andReturn();

    }
}