package cl.sanos_y_salvos.ms_base.api.service;

import org.springframework.stereotype.Service;
import cl.sanos_y_salvos.ms_base.api.client.AuthClient;
import cl.sanos_y_salvos.ms_base.api.dto.LoginRequestDTO;
import cl.sanos_y_salvos.ms_base.api.dto.AuthResponseDTO; 
import cl.sanos_y_salvos.ms_base.api.dto.RegisterRequestDTO;
import cl.sanos_y_salvos.ms_base.api.dto.ValidateResponse;
import cl.sanos_y_salvos.ms_base.api.dto.UserDTO; 

@Service
public class AuthService {
    
    private final AuthClient authClient;
    private final UserService userService;

    public AuthService(AuthClient authClient, UserService userService) {
        this.authClient = authClient;
        this.userService = userService;
    }

    public AuthResponseDTO login(LoginRequestDTO loginRequest) {
        AuthResponseDTO authResponse = authClient.login(loginRequest);
        
        if (authResponse != null) {
            authResponse.setEmail(loginRequest.getEmail());
        }
        
        return authResponse;
    }

    public AuthResponseDTO register(RegisterRequestDTO registerRequest) {

        authClient.register(registerRequest);

        UserDTO userProfile = new UserDTO();
        userProfile.setName(registerRequest.name()); 
        userProfile.setLastName(registerRequest.lastName());
        userProfile.setEmail(registerRequest.email());
        userProfile.setPassword(registerRequest.password());
        userProfile.setPhoneNumber(registerRequest.phoneNumber());
        userProfile.setAddress(registerRequest.address());
        userProfile.setAddressNumber(registerRequest.addressNumber()); 
        userProfile.setCity(registerRequest.city());
        userProfile.setCountry(registerRequest.country());
        userProfile.setRole("user");
        
        userService.save(userProfile);

        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail(registerRequest.email());
        loginRequest.setPassword(registerRequest.password());
        
        return login(loginRequest);
    }

    public ValidateResponse validateToken(String token) {
        if (token != null && !token.startsWith("Bearer ")) {
            token = "Bearer " + token;
        }
        return authClient.validateToken(token);
    }
}