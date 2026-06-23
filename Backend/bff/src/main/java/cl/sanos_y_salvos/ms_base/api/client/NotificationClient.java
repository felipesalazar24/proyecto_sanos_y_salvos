package cl.sanos_y_salvos.ms_base.api.client;

import cl.sanos_y_salvos.ms_base.api.dto.NotificationDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NotificationClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${NOTIFICATIONS_URL:http://ms-notification:8085}")
    private String notificationServiceUrl;

    public NotificationDTO sendNotification(NotificationDTO notificationDTO) {
        String url = notificationServiceUrl + "/api/v1/notifications/send";
        return restTemplate.postForObject(url, notificationDTO, NotificationDTO.class);
    }
}