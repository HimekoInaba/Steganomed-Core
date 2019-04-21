package kz.stegano.med.controller;

import kz.stegano.med.model.Document;
import kz.stegano.med.model.dto.DocumentDto;
import kz.stegano.med.model.dto.EncryptRequest;
import kz.stegano.med.service.DocumentService;
import kz.stegano.med.service.JsonRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    private final JsonRequestService jsonRequestService;

    @Value("${settings.url}")
    private String url;

    private static final String API = "/api";
    private static final String DOCUMENT = API + "/doc";
    private static final String CREATE = DOCUMENT + "/create";
    private static final String ALL = DOCUMENT + "/getAllDocuments";
    private static final String GET_BY_ID = DOCUMENT + "/{id}";
    private static final String GET_BY_USER = DOCUMENT + "/getByUser";
    private static final String GET_BY_USER_ID = GET_BY_USER  + "/{id}";

    @PostMapping(CREATE)
    public Document createDocument(@RequestParam String message,
                                   @RequestParam String name,
                                   @RequestParam Long doctorId,
                                   @RequestParam Long userId,
                                   @RequestBody MultipartFile file) throws IOException {
        Document document = new Document();
        document.setUserId(userId);
        document.setDoctorId(doctorId);
        document.setName(name);
        String encryptedData = jsonRequestService.sendRequest(
                jsonRequestService.prepareRequest(new EncryptRequest(file.getBytes(), message)), url + "/encode");
        return documentService.create(encryptedData, document);
    }

    @GetMapping(ALL)
    public List<DocumentDto> getAllDocuments() {
        return documentService.getAllDocuments();
    }

    @GetMapping(GET_BY_ID)
    public Document getByDocumentById(@PathVariable("id") Long id) {
        return documentService.getDocumentById(id);
    }

    @GetMapping(GET_BY_USER_ID)
    public List<DocumentDto> getDocumentByUserId (@PathVariable("id") Long userId) {
        return documentService.getDocumentsByUserId(userId);
    }
}
