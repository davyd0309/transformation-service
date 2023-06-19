package pl.transformation.transformationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import pl.transformation.transformationservice.template.xml.BeanTemplateXmlConfiguration;

@SpringBootApplication
@EnableWebMvc
@Import(BeanTemplateXmlConfiguration.class)
public class TransformationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransformationServiceApplication.class, args);
    }

}
