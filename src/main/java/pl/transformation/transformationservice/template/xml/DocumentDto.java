package pl.transformation.transformationservice.template.xml;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import org.bson.types.ObjectId;
@Getter
@AllArgsConstructor
@NoArgsConstructor
class DocumentDto {
    private String id;
    private String template;
}
