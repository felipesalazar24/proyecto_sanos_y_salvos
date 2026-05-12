package cl.sanos_y_salvos.ms_base.api.service;

import cl.sanos_y_salvos.ms_base.api.dto.LoginRequestDTO;
import cl.sanos_y_salvos.ms_base.api.dto.AuthUserDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    private final RestTemplate restTemplate;

    public AuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String login(LoginRequestDTO loginRequest) {
        String url = "http://ms-usuarios:8081/api/v1/users/auth-info?email=" + loginRequest.getEmail();

        try {
            AuthUserDTO user = restTemplate.getForObject(url, AuthUserDTO.class);

            if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
                return "TOKEN_SIMULADO_EXITOSO_ID_" + user.getId();
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Error: Usuario no encontrado o servicio de usuarios fuera de línea");
        }
        throw new RuntimeException("Credenciales incorrectas");
    }
}