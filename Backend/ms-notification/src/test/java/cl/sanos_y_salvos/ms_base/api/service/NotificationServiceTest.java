package cl.sanos_y_salvos.ms_base.api.service;

import cl.sanos_y_salvos.ms_base.api.dto.NotificationDTO;
import cl.sanos_y_salvos.ms_base.api.model.Notification;
import cl.sanos_y_salvos.ms_base.api.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    private Notification testNotification;
    private NotificationDTO testNotificationDTO;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();

        testNotification = new Notification();
        testNotification.setId(1L);
        testNotification.setTo("usuario@test.com");
        testNotification.setSubject("Alerta Sanos y Salvos");
        testNotification.setBody("Este es un mensaje de prueba.");
        testNotification.setSentAt(now);

        testNotificationDTO = new NotificationDTO();
        testNotificationDTO.setId(1L);
        testNotificationDTO.setTo("usuario@test.com");
        testNotificationDTO.setSubject("Alerta Sanos y Salvos");
        testNotificationDTO.setBody("Este es un mensaje de prueba.");
        testNotificationDTO.setSentAt(now);
    }

    @Test
    void createNotification_Success() {

        when(notificationRepository.save(any(Notification.class))).thenReturn(testNotification);

        NotificationDTO result = notificationService.createNotification(testNotificationDTO);

        assertNotNull(result);
        assertEquals("usuario@test.com", result.getTo());
        assertEquals("Alerta Sanos y Salvos", result.getSubject());
        
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void getNotificationById_Success() {

        when(notificationRepository.findById(1L)).thenReturn(Optional.of(testNotification));

        NotificationDTO result = notificationService.getNotificationById(1L);

        assertNotNull(result);
        assertEquals("usuario@test.com", result.getTo());
        verify(notificationRepository, times(1)).findById(1L);
    }

    @Test
    void getNotificationById_ThrowsException_WhenNotFound() {

        when(notificationRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            notificationService.getNotificationById(99L);
        });
        
        assertEquals("Notificación no encontrada con el id: 99", exception.getMessage());
        verify(notificationRepository, times(1)).findById(99L);
    }

    @Test
    void getAllNotifications_Success() {

        when(notificationRepository.findAll()).thenReturn(Arrays.asList(testNotification));

        List<NotificationDTO> result = notificationService.getAllNotifications();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("usuario@test.com", result.get(0).getTo());
        verify(notificationRepository, times(1)).findAll();
    }

    @Test
    void deleteNotification_Success() {

        when(notificationRepository.existsById(1L)).thenReturn(true);
        doNothing().when(notificationRepository).deleteById(1L);

        notificationService.deleteNotification(1L);

        verify(notificationRepository, times(1)).existsById(1L);
        verify(notificationRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteNotification_ThrowsException_WhenNotFound() {

        when(notificationRepository.existsById(99L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            notificationService.deleteNotification(99L);
        });

        assertEquals("Notificación no encontrada con el id: 99", exception.getMessage());
        verify(notificationRepository, times(1)).existsById(99L);
        verify(notificationRepository, never()).deleteById(anyLong());
    }
}