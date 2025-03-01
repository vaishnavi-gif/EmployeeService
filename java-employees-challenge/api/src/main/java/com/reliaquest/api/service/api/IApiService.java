package com.reliaquest.api.service.api;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface IApiService {
    ResponseEntity<JsonNode> get(String endpoint);

    ResponseEntity<JsonNode> post(String endpoint, Map<String, Object> employeeInput);

    void delete(String endpoint);
}
