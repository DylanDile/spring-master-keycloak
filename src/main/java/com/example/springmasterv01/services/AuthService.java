package com.example.springmasterv01.services;

import com.example.springmasterv01.models.AuthModel;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Service
public class AuthService extends  CoreService{

    private final RestTemplate restTemplate;

    public AuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<JsonNode> call(AuthModel model) {
        ObjectMapper objectMapper = new ObjectMapper();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        System.out.println("Used client_id is: " + clientId);
        map.add("client_id", clientId);
        map.add("username", model.getUsername());
        map.add("password", model.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestBodyFormUrlEncoded = new HttpEntity<>(map, headers);
        ResponseEntity<JsonNode> responseEntity = null;
        try {
            System.out.println("Used url is: " + url);
            responseEntity = restTemplate.postForEntity(url, requestBodyFormUrlEncoded, JsonNode.class);
        } catch (Exception e) {
            ObjectNode jsonNode = objectMapper.createObjectNode();
            jsonNode.put("error", e.getMessage());
            jsonNode.put("error_description", "Invalid user credentials");
            responseEntity = ResponseEntity.status(401).body(jsonNode);

        }
        return responseEntity;
    }
}
