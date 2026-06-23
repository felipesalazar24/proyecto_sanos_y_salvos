package cl.sanos_y_salvos.ms_base.api.repository;

import cl.sanos_y_salvos.ms_base.api.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByToOrderBySentAtDesc(String to);
}