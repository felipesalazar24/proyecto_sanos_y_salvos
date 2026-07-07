package cl.sanos_y_salvos.ms_base.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.sanos_y_salvos.ms_base.api.dto.NotificationDTO;
import cl.sanos_y_salvos.ms_base.api.service.NotificationService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/bff/web/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<NotificationDTO> sendNotification(@Valid @RequestBody NotificationDTO notificationDTO) {
        NotificationDTO response = notificationService.dispatchNotification(notificationDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/notifications")
    public ResponseEntity<NotificationDTO> sendManualNotification(@RequestBody NotificationDTO notificationDTO) {
        NotificationDTO response = notificationService.dispatchNotification(notificationDTO);
        return ResponseEntity.ok(response);
    }
}