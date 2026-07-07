package cl.sanos_y_salvos.ms_base.api.controller;

import cl.sanos_y_salvos.ms_base.api.dto.NotificationDTO;
import cl.sanos_y_salvos.ms_base.api.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

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
    void sendNotification_Success() {

        when(notificationService.dispatchNotification(any(NotificationDTO.class))).thenReturn(testNotification);

        ResponseEntity<NotificationDTO> response = notificationController.sendNotification(testNotification);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("usuario@test.com", response.getBody().getTo());
        verify(notificationService, times(1)).dispatchNotification(testNotification);
    }

    @Test
    void sendManualNotification_Success() {

        when(notificationService.dispatchNotification(any(NotificationDTO.class))).thenReturn(testNotification);

        ResponseEntity<NotificationDTO> response = notificationController.sendManualNotification(testNotification);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("usuario@test.com", response.getBody().getTo());
        verify(notificationService, times(1)).dispatchNotification(testNotification);
    }
}