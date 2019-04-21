package kz.stegano.med.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EncryptRequest {
    byte[] file;
    String message;
}
