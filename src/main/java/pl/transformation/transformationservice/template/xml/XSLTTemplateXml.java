package pl.transformation.transformationservice.template.xml;

import lombok.*;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class XSLTTemplateXml {
    private String id;
    @NonNull
    private String template;
}
