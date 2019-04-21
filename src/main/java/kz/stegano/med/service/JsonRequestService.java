package kz.stegano.med.service;

public interface JsonRequestService {
    String sendRequest(String jsonRequest, String url);

    String prepareRequest(Object request);
}
