package cl.sanos_y_salvos.ms_base.api.service;

import cl.sanos_y_salvos.ms_base.api.client.NotificationClient;
import cl.sanos_y_salvos.ms_base.api.dto.NotificationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationClient notificationClient;

    @InjectMocks
    private NotificationService notificationService;

    private NotificationDTO testNotification;

    @BeforeEach
    void setUp() {
        testNotification = new NotificationDTO();
        testNotification.setId(1L);
        testNotification.setTo("usuario@test.com");
        testNotification.setSubject("Alerta de Prueba");
        testNotification.setBody("Este es el cuerpo del mensaje");
        testNotification.setSentAt(LocalDateTime.now());
    }

    @Test
    void dispatchNotification_Success() {

        when(notificationClient.sendNotification(any(NotificationDTO.class))).thenReturn(testNotification);

        NotificationDTO result = notificationService.dispatchNotification(testNotification);

        assertNotNull(result);
        assertEquals("usuario@test.com", result.getTo());
        verify(notificationClient, times(1)).sendNotification(testNotification);
    }
}