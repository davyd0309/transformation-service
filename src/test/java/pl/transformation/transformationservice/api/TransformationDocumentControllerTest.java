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
    public void testGenerateDocument() throws Exception {
        String templateId = "template-id";
        String xmlData = "<xmlData><person><name>Jan Serce</name><age>30</age><info>additional information saved xml</info></person></xmlData>";
        String templateSavedType = "xml";
        boolean logDocument = false;
        boolean asynchronous = false;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/template/generate/{templateId}", templateId)
                        .contentType(MediaType.APPLICATION_XML)
                        .content(xmlData)
                        .param("templateSavedType", templateSavedType)
                        .param("logDocument", String.valueOf(logDocument))
                        .param("asynchronous", String.valueOf(asynchronous)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_XML));
    }
}