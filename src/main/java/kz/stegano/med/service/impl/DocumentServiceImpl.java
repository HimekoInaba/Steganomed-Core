package kz.stegano.med.service.impl;

import kz.stegano.med.exception.DefaultException;
import kz.stegano.med.model.Document;
import kz.stegano.med.model.dto.DocumentDto;
import kz.stegano.med.repository.DocumentRepository;
import kz.stegano.med.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;

    @Override
    public Document create(String encryptedData, Document document) {
        document.setContent(encryptedData.getBytes());
        return documentRepository.save(document);
    }

    @Override
    public Document getDocumentById(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new DefaultException(String.format("Can't find document by id: %d", id)));
    }

    @Override
    public List<DocumentDto> getAllDocuments() {
        List<DocumentDto> list = new ArrayList<>();
        documentRepository.findAll().forEach(document -> list.add(mapToDto(document)));
        return list;
    }

    @Override
    public List<DocumentDto> getDocumentsByUserId(Long userId) {
        return documentRepository.getAllByUserId(userId)
                .stream()
                .map(this::mapToDto)
                .collect(toList());
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
