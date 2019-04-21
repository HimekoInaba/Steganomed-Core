package kz.stegano.med.service.impl;

import io.jsonwebtoken.lang.Collections;
import kz.stegano.med.exception.DefaultException;
import kz.stegano.med.model.Document;
import kz.stegano.med.model.dto.DocumentDto;
import kz.stegano.med.repository.DocumentRepository;
import kz.stegano.med.service.DocumentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private DocumentRepository documentRepository;

    @Override
    public Document create(String encryptedData, Document document) throws IOException {
        document.setContent(encryptedData.getBytes());
        return documentRepository.save(document);
    }

    @Override
    public Document getDocumentById(Long id) {
        Optional<Document> document = documentRepository.findById(id);
        if (document.isPresent()) {
            return document.get();
        }
        throw new DefaultException("");
    }

    @Override
    public List<DocumentDto> getAllDocuments() {
        Iterable<Document> iterable = documentRepository.findAll();
        if (iterable.iterator().hasNext()) {
            List<DocumentDto> list = new ArrayList<>();
            iterable.forEach(it -> list.add(mapToDto(it)));
            return list;
        }
        throw new DefaultException("");
    }

    @Override
    public List<DocumentDto> getDocumentsByUserId(Long userId) {
        List<Document> documentListOfUser = documentRepository.getAllByUserId(userId);
        if (!Collections.isEmpty(documentListOfUser)) {
           return documentListOfUser.stream()
                    .map(this::mapToDto)
                    .collect(toList());
        }
        throw new DefaultException("");
    }

    private DocumentDto mapToDto(Document document) {
        DocumentDto dto = new DocumentDto();
        dto.setDoctorId(document.getDoctorId());
        dto.setUserId(document.getUserId());
        dto.setCreationTime(document.getCreationTime());
        dto.setContent("data:image/bmp;base64," + new String(document.getContent()));
        dto.setName(document.getName());
        return dto;
    }
}
