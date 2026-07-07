package cl.sanos_y_salvos.ms_base.api.service;

import cl.sanos_y_salvos.ms_base.api.model.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @Mock
    private JwtEncoder jwtEncoder;

    private JwtService jwtService;
    private UserAccount testUser;

    @BeforeEach
    void setUp() {

        jwtService = new JwtService(jwtEncoder, "sanos-y-salvos-issuer", 30L);

        testUser = new UserAccount();
        testUser.setEmail("felipe@test.com");
        testUser.setRole("admin");
    }

    @Test
    void generateAccessToken_Success() {

        Jwt mockJwt = mock(Jwt.class);
        when(mockJwt.getTokenValue()).thenReturn("mocked.jwt.token");
        when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(mockJwt);

        String token = jwtService.generateAccessToken(testUser);

        assertNotNull(token);
        assertEquals("mocked.jwt.token", token);
        
        verify(jwtEncoder, times(1)).encode(any(JwtEncoderParameters.class));
    }

    @Test
    void getAccessTokenTtlSeconds_ReturnsCorrectValue() {
        long ttlSeconds = jwtService.getAccessTokenTtlSeconds();
        assertEquals(1800L, ttlSeconds); // 30 minutos * 60 segundos
    }
}