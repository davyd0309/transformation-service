package pl.transformation.transformationservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/template")
public class XmlTransformationController {

    @GetMapping
    public String generateDocument() {

    }

}
