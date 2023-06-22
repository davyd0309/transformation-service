package pl.transformation.transformationservice.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TransformationDocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGenerateDocumentSync() throws Exception {
        String templateId = "template-id";
        String xmlData = "<xmlData><person><name>Jan Serce</name><age>30</age><info>additional information saved xml</info></person></xmlData>";
        String templateSavedType = "xml";
        boolean logDocument = false;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/template/{templateId}/generate", templateId)
                        .contentType(MediaType.APPLICATION_XML)
                        .content(xmlData)
                        .param("templateSavedType", templateSavedType)
                        .param("logDocument", String.valueOf(logDocument)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_XML));
    }

    @Test
    public void testGenerateDocumentAsync() throws Exception {
        String templateId = "template-id";
        String xmlData = "<xmlData><person><name>Jan Serce</name><age>30</age><info>additional information saved xml</info></person></xmlData>";
        String templateSavedType = "xml";
        boolean logDocument = false;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/template/{templateId}/generate/async", templateId)
                        .contentType(MediaType.APPLICATION_XML)
                        .content(xmlData)
                        .param("templateSavedType", templateSavedType)
                        .param("logDocument", String.valueOf(logDocument)))
                .andExpect(status().isOk());
    }
}