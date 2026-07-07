package cl.sanos_y_salvos.ms_base.api.client;

import cl.sanos_y_salvos.ms_base.api.dto.NotificationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private NotificationClient notificationClient;

    private NotificationDTO testNotification;
    private final String mockUrl = "http://localhost:8085";

    @BeforeEach
    void setUp() {

        ReflectionTestUtils.setField(notificationClient, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(notificationClient, "notificationServiceUrl", mockUrl);

        testNotification = new NotificationDTO();
        testNotification.setId(1L);
        testNotification.setTo("usuario@test.com");
        testNotification.setSubject("Alerta de Prueba");
        testNotification.setBody("Este es el cuerpo del mensaje");
        testNotification.setSentAt(LocalDateTime.now());
    }

    @Test
    void sendNotification_Success() {

        String expectedUrl = mockUrl + "/api/v1/notifications/send";
        when(restTemplate.postForObject(eq(expectedUrl), any(NotificationDTO.class), eq(NotificationDTO.class)))
                .thenReturn(testNotification);

        NotificationDTO result = notificationClient.sendNotification(testNotification);

        assertNotNull(result);
        assertEquals("usuario@test.com", result.getTo());
        assertEquals("Alerta de Prueba", result.getSubject());
    }
}