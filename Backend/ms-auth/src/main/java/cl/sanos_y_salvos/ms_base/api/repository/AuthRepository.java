package cl.sanos_y_salvos.ms_base.api.repository;

import cl.sanos_y_salvos.ms_base.api.model.AuthUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<AuthUser, Long> {
    
    Optional<AuthUser> findByEmail(String email);
    
}
