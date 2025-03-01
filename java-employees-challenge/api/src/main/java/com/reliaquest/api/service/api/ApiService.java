package com.reliaquest.api.service.api;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ApiService implements IApiService {


    private final RestTemplate restTemplate;
    private String apiBaseUrl;
    private static final Logger logger = LoggerFactory.getLogger(ApiService.class);

    @Autowired
    public ApiService(RestTemplate restTemplate, @Value("${api.base.url}") String apiBaseUrl) {
        this.restTemplate = restTemplate;
        this.apiBaseUrl = apiBaseUrl;
    }

    @Override
    public ResponseEntity<JsonNode> get(String endpoint) {
        logger.info("GET Api call for: {}{}", apiBaseUrl, endpoint);
        return restTemplate.getForEntity(apiBaseUrl + endpoint, JsonNode.class);
    }

    @Override
    public ResponseEntity<JsonNode> post(String endpoint, Map<String, Object> request) {
        logger.info("POST Api call for: {}{}", apiBaseUrl, endpoint);
        return restTemplate.postForEntity(apiBaseUrl + endpoint, request, JsonNode.class);
    }

    @Override
    public void delete(String endpoint) {
        logger.info("DELETE Api call for: {}{}", apiBaseUrl, endpoint);
        restTemplate.delete(apiBaseUrl + endpoint);
    }
}
