package cl.sanos_y_salvos.ms_base.api.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificationDTO {
    private Long id;
    private String to;
    private String subject;
    private String body;
    private LocalDateTime sentAt;
}