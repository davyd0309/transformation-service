package pl.transformation.transformationservice.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.transformation.transformationservice.template.json.TransformationTemplateJsonService;
import pl.transformation.transformationservice.template.json.XSLTTemplateJson;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TransformationTemplateJsonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransformationTemplateJsonService transformationTemplateJsonService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new TransformationTemplateJsonController(transformationTemplateJsonService))
                .build();
    }

    @Test
    public void testCreateTemplate() throws Exception {
        String requestBody = "{\"filename\": \"PersonalDataChange\", \"description\": \"Template use for add information about user\", \"xsltContent\": \"<xsl:stylesheet version=\\\"1.0\\\" xmlns:xsl=\\\"http://www.w3.org/1999/XSL/Transform\\\">\\r\\n <xsl:output method=\\\"xml\\\" indent=\\\"yes\\\"/>\\r\\n <xsl:param name=\\\"infoParam\\\" select=\\\"'additional information saved json'\\\" />\\r\\n <xsl:template match=\\\"/\\\">\\r\\n <xsl:copy>\\r\\n <xsl:apply-templates select=\\\"\\\"/>\\r\\n </xsl:copy>\\r\\n </xsl:template>\\r\\n <xsl:template match=\\\"\\\">\\r\\n <xsl:copy>\\r\\n <xsl:copy-of select=\\\"@\\\"/>\\r\\n <xsl:apply-templates select=\\\"node()\\\"/>\\r\\n </xsl:copy>\\r\\n </xsl:template>\\r\\n <xsl:template match=\\\"text()\\\">\\r\\n <xsl:if test=\\\"normalize-space(.) != ''\\\">\\r\\n <xsl:value-of select=\\\"normalize-space(.)\\\"/>\\r\\n </xsl:if>\\r\\n </xsl:template>\\r\\n <xsl:template match=\\\"person\\\">\\r\\n <xsl:copy>\\r\\n <xsl:apply-templates select=\\\"@|node()\\\"/>\\r\\n <info><xsl:value-of select=\\\"normalize-space($infoParam)\\\"/></info>\\r\\n </xsl:copy>\\r\\n </xsl:template>\\r\\n</xsl:stylesheet>\"}";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/template/json")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.filename").value("PersonalDataChange"))
                .andExpect(jsonPath("$.description").value("Template use for add information about user"))
                .andExpect(jsonPath("$.xsltContent").value(containsString("additional information saved json")))
                .andReturn();
    }

    @Test
    public void testGetTemplateBy_Filename() throws Exception {
        XSLTTemplateJson template = new XSLTTemplateJson("1", "PersonalDataChange", "Template use for add information about user", "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">...</xsl:stylesheet>");
        transformationTemplateJsonService.createTemplate(template);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/template/json")
                        .param("filename", "PersonalDataChange"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.filename").value(template.filename()))
                .andExpect(jsonPath("$.description").value(template.description()))
                .andExpect(jsonPath("$.xsltContent").value(template.xsltContent()));
    }

    @Test
    public void testGetTemplateBy_Id() throws Exception {
        XSLTTemplateJson template = new XSLTTemplateJson("12", "PersonalDataChange", "Template use for add information about user", "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">...</xsl:stylesheet>");
        transformationTemplateJsonService.createTemplate(template);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/template/json")
                        .param("id", template.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.filename").value(template.filename()))
                .andExpect(jsonPath("$.description").value(template.description()))
                .andExpect(jsonPath("$.xsltContent").value(template.xsltContent()));
    }

    @Test
    public void testDeleteTemplate_Filename() throws Exception {
        XSLTTemplateJson template = new XSLTTemplateJson("1", "PersonalDataChange", "Template use for add information about user", "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">...</xsl:stylesheet>");
        transformationTemplateJsonService.createTemplate(template);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/template/json")
                        .param("filename", "PersonalDataChange"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteTemplate_Id() throws Exception {
        XSLTTemplateJson template = new XSLTTemplateJson("1", "PersonalDataChange", "Template use for add information about user", "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">...</xsl:stylesheet>");
        transformationTemplateJsonService.createTemplate(template);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/template/json")
                        .param("id", template.id()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testReplaceTemplateById() throws Exception {
        XSLTTemplateJson template = new XSLTTemplateJson("1323", "PersonalDataChange", "Template use for add information about user", "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">...</xsl:stylesheet>");
        transformationTemplateJsonService.createTemplate(template);

        String requestBody = "{\"filename\": \"PersonalDataChange2\", \"description\": \"Template use for add phone user\", \"xsltContent\": \"<xsl:stylesheet version=\\\"1.0\\\" xmlns:xsl=\\\"http://www.w3.org/1999/XSL/Transform\\\">\\r\\n <xsl:output method=\\\"xml\\\" indent=\\\"yes\\\"/>\\r\\n <xsl:param name=\\\"infoParam\\\" select=\\\"'664 533 111'\\\" />\\r\\n <xsl:template match=\\\"/\\\">\\r\\n <xsl:copy>\\r\\n <xsl:apply-templates select=\\\"\\\"/>\\r\\n </xsl:copy>\\r\\n </xsl:template>\\r\\n <xsl:template match=\\\"\\\">\\r\\n <xsl:copy>\\r\\n <xsl:copy-of select=\\\"@\\\"/>\\r\\n <xsl:apply-templates select=\\\"node()\\\"/>\\r\\n </xsl:copy>\\r\\n </xsl:template>\\r\\n <xsl:template match=\\\"text()\\\">\\r\\n <xsl:if test=\\\"normalize-space(.) != ''\\\">\\r\\n <xsl:value-of select=\\\"normalize-space(.)\\\"/>\\r\\n </xsl:if>\\r\\n </xsl:template>\\r\\n <xsl:template match=\\\"person\\\">\\r\\n <xsl:copy>\\r\\n <xsl:apply-templates select=\\\"@|node()\\\"/>\\r\\n <xsl:value-of select=\\\"normalize-space($infoParam)\\\"/>\\r\\n </xsl:copy>\\r\\n </xsl:template>\\r\\n</xsl:stylesheet>\"}";

        mockMvc.perform(MockMvcRequestBuilders.put("/api/template/json/{id}", template.id())
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.filename").value("PersonalDataChange2"))
                .andExpect(jsonPath("$.description").value("Template use for add phone user"))
                .andExpect(jsonPath("$.xsltContent").value(containsString("664 533 111")));
    }
}