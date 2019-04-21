package kz.stegano.med.controller;

import kz.stegano.med.model.dto.EncryptRequest;
import kz.stegano.med.service.JsonRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class BmpController {
    @Value("${settings.url}")
    private String urlPath;

    private JsonRequestService jsonRequestService;

    @Autowired
    public BmpController(JsonRequestService jsonRequestService) {
        this.jsonRequestService = jsonRequestService;
    }

    @PostMapping("/encode")
    public String encode(@RequestParam String message, @RequestBody MultipartFile file) throws IOException {
        String response = jsonRequestService.sendRequest(jsonRequestService.prepareRequest(new EncryptRequest(file.getBytes(), message)), urlPath + "/encode");
        return response;
    }

    @PostMapping("/decode")
    public String decode(@RequestBody MultipartFile file) throws IOException {
        return jsonRequestService.sendRequest(jsonRequestService.prepareRequest(file.getBytes()), urlPath + "/decode");
    }
}
