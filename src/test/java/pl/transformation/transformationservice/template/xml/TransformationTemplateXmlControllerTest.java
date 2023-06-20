package pl.transformation.transformationservice.template.xml;

import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.transformation.transformationservice.config.MongoConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
 class TransformationTemplateXmlControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetAllTemplates() {
        // When
        ResponseEntity<List<XSLTTemplateXml>> response = restTemplate.exchange("/api/template/xml",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<XSLTTemplateXml>>() {});

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<XSLTTemplateXml> templates = response.getBody();
        assertThat(templates).isNotNull();
        // Additional assertions for the returned templates
    }

    @Test
    void testGetTemplateById() {
        // Given
        String templateId = "1";

        // When
        ResponseEntity<XSLTTemplateXml> response = restTemplate.getForEntity("/api/template/xml/{id}",
                XSLTTemplateXml.class, templateId);

        // Then
        if (response.getStatusCode() == HttpStatus.OK) {
            XSLTTemplateXml template = response.getBody();
            assertThat(template).isNotNull();
            assertThat(template.getId()).isEqualTo(templateId);
            // Additional assertions for the template
        } else if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            // Handle case when template not found
        }
    }

    @Test
    void testDeleteTemplate() {
        // Given
        String templateId = "1";

        // When
        ResponseEntity<Void> response = restTemplate.exchange("/api/template/xml/{id}",
                HttpMethod.DELETE, null, Void.class, templateId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}