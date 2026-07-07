package cl.sanos_y_salvos.ms_base.api.service;

import cl.sanos_y_salvos.ms_base.api.dto.AuthResponseDTO;
import cl.sanos_y_salvos.ms_base.api.dto.LoginRequestDTO;
import cl.sanos_y_salvos.ms_base.api.dto.RegisterRequest;
import cl.sanos_y_salvos.ms_base.api.model.UserAccount;
import cl.sanos_y_salvos.ms_base.api.repository.AuthRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthRepository authRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    private UserAccount testUser;

    @BeforeEach
    void setUp() {
        testUser = new UserAccount();
        testUser.setId(1L);
        testUser.setEmail("felipe@test.com");
        testUser.setPassword("encoded_password");
        testUser.setRole("user");
        testUser.setEnabled(true);
    }

    @Test
    void register_Success() {
        RegisterRequest request = new RegisterRequest("nuevo@test.com", "password123");
        
        when(authRepository.existsByEmailIgnoreCase("nuevo@test.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encoded_password");
        
        authService.register(request);
        
        verify(authRepository, times(1)).save(any(UserAccount.class));
    }

    @Test
    void register_ThrowsException_WhenEmailExists() {
        RegisterRequest request = new RegisterRequest("felipe@test.com", "password123");
        
        when(authRepository.existsByEmailIgnoreCase("felipe@test.com")).thenReturn(true);
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.register(request);
        });
        
        assertEquals("Email already in use", exception.getMessage());
        verify(authRepository, never()).save(any(UserAccount.class));
    }

    @Test
    void login_Success() {
        LoginRequestDTO request = new LoginRequestDTO("felipe@test.com", "password123");
        
        when(authRepository.findByEmailIgnoreCase("felipe@test.com")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("password123", "encoded_password")).thenReturn(true);
        when(jwtService.generateAccessToken(testUser)).thenReturn("fake-jwt-token");
        when(jwtService.getAccessTokenTtlSeconds()).thenReturn(1800L);
        
        AuthResponseDTO response = authService.login(request);
        
        assertNotNull(response);
        assertEquals("fake-jwt-token", response.accessToken());
        assertEquals("Bearer", response.tokenType());
        assertEquals(1800L, response.expiresIn());
    }

    @Test
    void login_ThrowsException_WhenUserNotFound() {
        LoginRequestDTO request = new LoginRequestDTO("noexiste@test.com", "password123");
        
        when(authRepository.findByEmailIgnoreCase("noexiste@test.com")).thenReturn(Optional.empty());
        
        assertThrows(BadCredentialsException.class, () -> {
            authService.login(request);
        });
    }

    @Test
    void login_ThrowsException_WhenPasswordIsWrong() {
        LoginRequestDTO request = new LoginRequestDTO("felipe@test.com", "wrongpassword");
        
        when(authRepository.findByEmailIgnoreCase("felipe@test.com")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("wrongpassword", "encoded_password")).thenReturn(false);
        
        assertThrows(BadCredentialsException.class, () -> {
            authService.login(request);
        });
    }

    @Test
    void login_ThrowsException_WhenUserIsDisabled() {
        testUser.setEnabled(false);
        LoginRequestDTO request = new LoginRequestDTO("felipe@test.com", "password123");
        
        when(authRepository.findByEmailIgnoreCase("felipe@test.com")).thenReturn(Optional.of(testUser));
        // No necesitamos mockear passwordEncoder porque la evaluación corta circuito (short-circuit) falla primero en el getEnabled()
        
        assertThrows(BadCredentialsException.class, () -> {
            authService.login(request);
        });
    }
}