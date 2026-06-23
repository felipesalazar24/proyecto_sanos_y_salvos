package cl.sanos_y_salvos.ms_base.api.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private String to;
    private String subject;
    private String body;
    private LocalDateTime sentAt;
}