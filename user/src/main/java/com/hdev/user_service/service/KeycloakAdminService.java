package com.hdev.user_service.service;

import com.hdev.user_service.dto.UserRequestDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KeycloakAdminService {
    private final RestTemplate restTemplate = new RestTemplate();

    public String getAdminToken(){
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("client_id", "admin-cli");
        requestBody.add("username", "user");
        requestBody.add("password", "user");
        requestBody.add("grant_type", "password");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> http = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity("http://localhost:8999/realms/ecommerce/protocol/openid-connect/token", http, Map.class);
        return (String) response.getBody().get("access_token");
    }

    public String createUser(String token, UserRequestDTO userRequest){
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("username", userRequest.getUsername());
        requestBody.put("email", userRequest.getEmail());
        requestBody.put("enabled", true);
        requestBody.put("firstName", userRequest.getFirstName());
        requestBody.put("lastName", userRequest.getLastName());

        Map<String, Object> credentials = new HashMap<>();
        credentials.put("type", "password");
        credentials.put("value", userRequest.getPassword());
        credentials.put("temporary", false);
        requestBody.put("credentials", List.of(credentials));

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> http = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Object> response = restTemplate.postForEntity("http://localhost:8999/admin/realms/ecommerce/users", http, Object.class);

        if(!HttpStatus.CREATED.equals(response.getStatusCode())){
            throw new RuntimeException("Failed to create user on Keycloak");
        }

        String uri = response.getHeaders().getLocation().getPath();
        return uri.substring(uri.lastIndexOf("/") + 1);
    }

    public void assignRole(String token, String keycloakUserId, String roleName){
        Map<String, Object> roleRepresentation = getRoleRepresentation(token, roleName);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<Map<String, Object>>> http = new HttpEntity<>(List.of(roleRepresentation), headers);
        ResponseEntity<Object> response = restTemplate.postForEntity(
                "http://localhost:8999/admin/realms/ecommerce/users/{userId}/role-mappings/clients/de9a377b-9b33-4aab-90ed-666e4e89ca5e",
                http,
                Object.class,
                keycloakUserId);
        if(!response.getStatusCode().is2xxSuccessful()){
            throw new RuntimeException("Could assign role to user " + keycloakUserId);
        }
    }

    private Map<String, Object> getRoleRepresentation(String token, String roleName){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Map<String, Object>> http = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(
                "http://localhost:8999/admin/realms/ecommerce/clients/de9a377b-9b33-4aab-90ed-666e4e89ca5e/roles/{role}",
                HttpMethod.GET,
                http,
                Map.class,
                roleName);
        if(!response.getStatusCode().is2xxSuccessful()){
            throw new RuntimeException("Could not find role " + roleName);
        }
        return response.getBody();
    }
}
