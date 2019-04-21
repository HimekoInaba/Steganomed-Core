package kz.stegano.med.model.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class DocumentDto {
    private Long id;
    private String name;
    private String content;
    private Date creationTime;
    private Long doctorId;
    private Long userId;
}
