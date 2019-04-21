package kz.stegano.med.service;

import kz.stegano.med.model.Document;
import kz.stegano.med.model.dto.DocumentDto;

import java.io.IOException;
import java.util.List;

public interface DocumentService {
    Document create(String encryptedData, Document document) throws IOException;

    Document getDocumentById(Long id);

    List<DocumentDto> getAllDocuments();

    List<DocumentDto> getDocumentsByUserId(Long userId);
}
