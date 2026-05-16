package cl.sanos_y_salvos.ms_base.api.service;

import org.springframework.stereotype.Service;
import cl.sanos_y_salvos.ms_base.api.client.AuthClient;
import cl.sanos_y_salvos.ms_base.api.dto.LoginRequestDTO;
import cl.sanos_y_salvos.ms_base.api.dto.AuthResponseDTO; // 1. Importamos el DTO simplificado

@Service
public class AuthService {
    
    private final AuthClient authClient;

    public AuthService(AuthClient authClient) {
        this.authClient = authClient;
    }

    public AuthResponseDTO login(LoginRequestDTO request){
        return authClient.callLogin(request);
    }    
}