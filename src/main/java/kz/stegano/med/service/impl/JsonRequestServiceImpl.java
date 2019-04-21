package kz.stegano.med.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import kz.stegano.med.service.JsonRequestService;
import kz.stegano.med.util.IntegratorJsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class JsonRequestServiceImpl implements JsonRequestService {
    private RestTemplate httpclient = new RestTemplate();


    @Override
    public String sendRequest(String jsonRequest, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String response = null;
        log.info("Sending to url = " + url);
        if (!StringUtils.isEmpty(jsonRequest)) {
            HttpEntity entity = new HttpEntity<>(jsonRequest, headers);
            log.info("Sending data to : " + url);
            try {
                    response = httpclient.postForObject(url, entity, String.class);
            } catch (RestClientException e) {
                log.error("Exception occurred while sending request {}", e.getMessage());
            }
        } else {
            try {
                response = httpclient.getForObject(url, String.class);
            } catch (RestClientException e) {
                log.error("Exception occurred while sending request {}", e.getMessage());
            }
        }
        log.info("Response is {}", response);
        return response;
    }

    @Override
    public String prepareRequest(Object request) {
        String jsonRequest;
        try {
            jsonRequest = IntegratorJsonUtil.toJson(request);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return null;
        }
        return jsonRequest;
    }
}

