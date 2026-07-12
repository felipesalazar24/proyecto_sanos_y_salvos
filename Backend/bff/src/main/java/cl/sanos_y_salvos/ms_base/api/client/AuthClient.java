package cl.sanos_y_salvos.ms_base.api.client;

import cl.sanos_y_salvos.ms_base.api.dto.LoginRequestDTO;
import cl.sanos_y_salvos.ms_base.api.dto.RegisterRequest;
import cl.sanos_y_salvos.ms_base.api.dto.AuthResponseDTO;
import cl.sanos_y_salvos.ms_base.api.dto.ValidateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

@Component
public class AuthClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public AuthClient(RestTemplate restTemplate, @Value("${endpoints.ms-auth}") String baseUrl) {
        this.restTemplate = restTemplate;
        String sanitizedUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        this.baseUrl = sanitizedUrl + "/api/v1/auth";
    }

    public AuthResponseDTO login(LoginRequestDTO loginRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); 
        
        HttpEntity<LoginRequestDTO> entity = new HttpEntity<>(loginRequest, headers);

        return restTemplate.postForObject(baseUrl + "/login", entity, AuthResponseDTO.class);
    }

    public AuthResponseDTO register(RegisterRequest registerRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    
        HttpEntity<RegisterRequest> entity = new HttpEntity<>(registerRequest, headers);

        return restTemplate.postForObject(baseUrl + "/register", entity, AuthResponseDTO.class);
    }

    public ValidateResponse validateToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                baseUrl + "/validate",
                HttpMethod.GET,
                entity,
                ValidateResponse.class
        ).getBody();
    }
}