package cl.sanos_y_salvos.ms_base.api.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import cl.sanos_y_salvos.ms_base.api.dto.LoginRequestDTO;

@Component
public class AuthClient {
    
    private final RestTemplate restTemplate;
    private final String authServiceUrl = "http://ms-auth:8083/api/v1/auth/login"; 

    public AuthClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String callLogin(LoginRequestDTO request){
        return restTemplate.postForObject(authServiceUrl, request, String.class);
    }

}
