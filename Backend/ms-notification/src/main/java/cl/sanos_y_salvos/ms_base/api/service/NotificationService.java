package cl.sanos_y_salvos.ms_base.api.service;

import cl.sanos_y_salvos.ms_base.api.dto.NotificationDTO;
import cl.sanos_y_salvos.ms_base.api.model.Notification;
import cl.sanos_y_salvos.ms_base.api.repository.NotificationRepository;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Transactional
    public NotificationDTO createNotification(NotificationDTO notificationDTO) {
        // Configuración básica SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("tu-correo@duocuc.cl", "tu-clave");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("tu-correo@duocuc.cl"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(notificationDTO.getTo()));
            message.setSubject(notificationDTO.getSubject());
            message.setText(notificationDTO.getBody());
            
            System.out.println("📨 Correo enviado con éxito usando Jakarta Mail nativo");
        } catch (MessagingException e) {
            System.err.println("Error al enviar correo SMTP: " + e.getMessage());
        }

        Notification notification = dtoToEntity(notificationDTO);
        Notification savedNotification = notificationRepository.save(notification);
        
        return entityToDto(savedNotification);
    }

    public NotificationDTO getNotificationById(Long id) {
        Notification notification = notificationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Notificación no encontrada con el id: " + id));
        return entityToDto(notification);
    }

    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteNotification(Long id) {
        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Notificación no encontrada con el id: " + id);
        }
    }

    private Notification dtoToEntity(NotificationDTO dto) {
        Notification entity = new Notification();
        entity.setTo(dto.getTo());
        entity.setSubject(dto.getSubject());
        entity.setBody(dto.getBody());
        return entity;
    }

    private NotificationDTO entityToDto(Notification entity) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(entity.getId());
        dto.setTo(entity.getTo());
        dto.setSubject(entity.getSubject());
        dto.setBody(entity.getBody());
        dto.setSentAt(entity.getSentAt());
        return dto;
    }
}