package cl.sanos_y_salvos.ms_base.api.controller;

import cl.sanos_y_salvos.ms_base.api.dto.AuthResponseDTO;
import cl.sanos_y_salvos.ms_base.api.dto.LoginRequestDTO;
import cl.sanos_y_salvos.ms_base.api.dto.RegisterRequestDTO;
import cl.sanos_y_salvos.ms_base.api.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private LoginRequestDTO loginRequest;
    private RegisterRequestDTO registerRequest;
    private AuthResponseDTO authResponse;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequestDTO();
        loginRequest.setEmail("felipe@test.com");
        loginRequest.setPassword("password123");

        registerRequest = new RegisterRequestDTO(
                "Felipe", "Test", "felipe@test.com", "password123",
                98765432, "Calle Falsa", 123, "Santiago", "Chile"
        );

        authResponse = AuthResponseDTO.builder()
                .accessToken("mocked-token")
                .tokenType("Bearer")
                .expiresIn(3600L)
                .email("felipe@test.com")
                .build();
    }

    @Test
    void login_Success() {

        when(authService.login(any(LoginRequestDTO.class))).thenReturn(authResponse);

        ResponseEntity<AuthResponseDTO> response = authController.login(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("mocked-token", response.getBody().getAccessToken());
        assertEquals("felipe@test.com", response.getBody().getEmail());
        
        verify(authService, times(1)).login(loginRequest);
    }

    @Test
    void register_Success() {

        when(authService.register(any(RegisterRequestDTO.class))).thenReturn(authResponse);

        ResponseEntity<AuthResponseDTO> response = authController.register(registerRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("mocked-token", response.getBody().getAccessToken());
        assertEquals("felipe@test.com", response.getBody().getEmail());
        
        verify(authService, times(1)).register(registerRequest);
    }
}