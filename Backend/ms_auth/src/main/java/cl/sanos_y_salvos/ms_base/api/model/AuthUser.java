package cl.sanos_y_salvos.ms_base.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@Builder
@Entity
@Table(name = "auth_users")
@AllArgsConstructor
@NoArgsConstructor
public class AuthUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", length = 30, unique = true, nullable = false)
    private String email;
    
    @Column(name = "password", length = 30, nullable = false)
    private String password;
}
