package cl.sanos_y_salvos.ms_base.api.client;

import cl.sanos_y_salvos.ms_base.api.dto.AuthResponseDTO;
import cl.sanos_y_salvos.ms_base.api.dto.LoginRequestDTO;
import cl.sanos_y_salvos.ms_base.api.dto.RegisterRequest;
import cl.sanos_y_salvos.ms_base.api.dto.ValidateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthClientTest {

    @Mock
    private RestTemplate restTemplate;

    private AuthClient authClient;
    private final String msAuthUrl = "http://localhost:8082";
    private final String baseUrl = msAuthUrl + "/api/v1/auth";

    private LoginRequestDTO loginRequest;
    private RegisterRequest registerRequest;
    private AuthResponseDTO authResponse;
    private ValidateResponse validateResponse;

    @BeforeEach
    void setUp() {
        authClient = new AuthClient(restTemplate, msAuthUrl);

        loginRequest = new LoginRequestDTO();
        loginRequest.setEmail("felipe@test.com");
        loginRequest.setPassword("password123");

        registerRequest = new RegisterRequest("felipe@test.com", "password123");

        authResponse = AuthResponseDTO.builder()
                .accessToken("mocked-token")
                .tokenType("Bearer")
                .expiresIn(3600L)
                .build();

        validateResponse = new ValidateResponse(true, "felipe@test.com", "issuer", Instant.now(), new HashMap<>());
    }

    @Test
    void login_Success() {
        when(restTemplate.postForObject(eq(baseUrl + "/api/v1/auth/login"), any(HttpEntity.class), eq(AuthResponseDTO.class)))
                .thenReturn(authResponse);

        AuthResponseDTO result = authClient.login(loginRequest);

        assertNotNull(result);
        assertEquals("mocked-token", result.getAccessToken());
    }

    @Test
    void register_Success() {
        when(restTemplate.postForObject(eq(baseUrl + "/api/v1/auth/register"), any(HttpEntity.class), eq(AuthResponseDTO.class)))
                .thenReturn(authResponse);

        AuthResponseDTO result = authClient.register(registerRequest);

        assertNotNull(result);
        assertEquals("mocked-token", result.getAccessToken());
    }

    @Test
    void validateToken_Success() {
        ResponseEntity<ValidateResponse> responseEntity = ResponseEntity.ok(validateResponse);
        when(restTemplate.exchange(eq(baseUrl + "/api/v1/auth/validate"), eq(HttpMethod.GET), any(HttpEntity.class), eq(ValidateResponse.class)))
                .thenReturn(responseEntity);

        ValidateResponse result = authClient.validateToken("Bearer mocked-token");

        assertNotNull(result);
        assertTrue(result.valid());
        assertEquals("felipe@test.com", result.subject());
    }
}