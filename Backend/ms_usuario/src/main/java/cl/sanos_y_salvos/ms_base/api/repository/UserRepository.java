package main.java.cl.sanos_y_salvos.ms_base.api.repository;

import main.java.cl.sanos_y_salvos.ms_base.api.model.DTO.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.sprongframework.data.jpa.repository.Query;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
