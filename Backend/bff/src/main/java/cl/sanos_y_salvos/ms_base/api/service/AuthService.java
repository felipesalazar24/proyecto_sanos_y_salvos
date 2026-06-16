package cl.sanos_y_salvos.ms_base.api.service;

import org.springframework.stereotype.Service;
import cl.sanos_y_salvos.ms_base.api.client.AuthClient;
import cl.sanos_y_salvos.ms_base.api.dto.LoginRequestDTO;
import cl.sanos_y_salvos.ms_base.api.dto.AuthResponseDTO; 
import cl.sanos_y_salvos.ms_base.api.dto.RegisterRequestDTO;
import cl.sanos_y_salvos.ms_base.api.dto.ValidateResponse;
    
@Service
public class AuthService {
    
    private final AuthClient authClient;

    public AuthService(AuthClient authClient) {
        this.authClient = authClient;
    }

    public AuthResponseDTO login(LoginRequestDTO loginRequest) {
        return authClient.login(loginRequest);
    }

    public AuthResponseDTO register(RegisterRequestDTO registerRequest) {
        return authClient.register(registerRequest);
    }

    public ValidateResponse validateToken(String token) {
        if (token != null && !token.startsWith("Bearer ")) {
            token = "Bearer " + token;
        }
        return authClient.validateToken(token);
    }
}