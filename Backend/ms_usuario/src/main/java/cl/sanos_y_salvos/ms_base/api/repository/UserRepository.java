package cl.sanos_y_salvos.ms_base.api.repository;

import cl.sanos_y_salvos.ms_base.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
