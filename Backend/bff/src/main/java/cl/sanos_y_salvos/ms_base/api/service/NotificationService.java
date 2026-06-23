package cl.sanos_y_salvos.ms_base.api.service;

import cl.sanos_y_salvos.ms_base.api.client.NotificationClient;
import cl.sanos_y_salvos.ms_base.api.dto.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private NotificationClient notificationClient;

    public NotificationDTO dispatchNotification(NotificationDTO notificationDTO) {
        return notificationClient.sendNotification(notificationDTO);
    }
}