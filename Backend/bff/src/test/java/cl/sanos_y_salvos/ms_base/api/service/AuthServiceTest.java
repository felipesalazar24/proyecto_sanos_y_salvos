package cl.sanos_y_salvos.ms_base.api.service;

import cl.sanos_y_salvos.ms_base.api.client.AuthClient;
import cl.sanos_y_salvos.ms_base.api.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthClient authClient;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthService authService;

    private LoginRequestDTO loginRequest;
    private RegisterRequestDTO registerRequestDTO;
    private AuthResponseDTO authResponse;
    private ValidateResponse validateResponse;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequestDTO();
        loginRequest.setEmail("felipe@test.com");
        loginRequest.setPassword("password123");

        registerRequestDTO = new RegisterRequestDTO(
                "Felipe", "Test", "felipe@test.com", "password123",
                98765432, "Calle Falsa", 123, "Santiago", "Chile"
        );

        authResponse = AuthResponseDTO.builder()
                .accessToken("mocked-token")
                .tokenType("Bearer")
                .expiresIn(3600L)
                .build();

        validateResponse = new ValidateResponse(true, "felipe@test.com", "issuer", Instant.now(), new HashMap<>());
    }

    @Test
    void login_Success_SetsEmailInResponse() {

        when(authClient.login(any(LoginRequestDTO.class))).thenReturn(authResponse);

        AuthResponseDTO result = authService.login(loginRequest);

        assertNotNull(result);
        assertEquals("felipe@test.com", result.getEmail());
        verify(authClient, times(1)).login(any(LoginRequestDTO.class));
    }

    @Test
    void login_ReturnsNull_WhenClientReturnsNull() {
        when(authClient.login(any(LoginRequestDTO.class))).thenReturn(null);
        
        AuthResponseDTO result = authService.login(loginRequest);
        
        assertNull(result);
    }

    @Test
    void register_Success_OrchestratesCallsAndAutoLogins() {

        when(authClient.register(any(RegisterRequest.class))).thenReturn(authResponse);
        when(userService.save(any(UserDTO.class))).thenReturn(new UserDTO());
        when(authClient.login(any(LoginRequestDTO.class))).thenReturn(authResponse);

        AuthResponseDTO result = authService.register(registerRequestDTO);

        assertNotNull(result);
        assertEquals("felipe@test.com", result.getEmail());
        
        verify(authClient, times(1)).register(any(RegisterRequest.class));
        
        verify(userService, times(1)).save(any(UserDTO.class));
        
        verify(authClient, times(1)).login(any(LoginRequestDTO.class));
    }

    @Test
    void validateToken_AddsBearerPrefixIfNotPresent() {
        when(authClient.validateToken("Bearer my-token")).thenReturn(validateResponse);
        
        ValidateResponse result = authService.validateToken("my-token");
        
        assertNotNull(result);
        verify(authClient, times(1)).validateToken("Bearer my-token");
    }

    @Test
    void validateToken_DoesNotAddBearerPrefixIfAlreadyPresent() {
        when(authClient.validateToken("Bearer my-token")).thenReturn(validateResponse);
        
        ValidateResponse result = authService.validateToken("Bearer my-token");
        
        assertNotNull(result);
        verify(authClient, times(1)).validateToken("Bearer my-token");
    }
}