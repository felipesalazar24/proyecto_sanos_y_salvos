package cl.sanos_y_salvos.ms_base.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@Builder
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 30, unique = false,nullable = false)
    private String name;

    @Column(name = "last_name", length = 30, unique = false, nullable = false)
    private String lastName;

    @Column(name = "email", length = 255, unique = true, nullable = false)
    private String email;
    
    @Column(name = "password", length = 255, nullable = false)
    private String password;
    
    @Column(name = "phone_number", unique = true, nullable = false)
    private int phoneNumber;
    
    @Column(name = "address", length = 100, nullable = false)
    private String address;
    
    @Column(name = "address_number", nullable = false)
    private int addressNumber;

    @Column(name = "city", length = 50, nullable = false)
    private String city;
    
    @Column(name = "country", length = 50, nullable = false)
    private String country;
    
    @Column(name = "role", length = 20, nullable = false, columnDefinition = "varchar(20) default 'user'")
    private String role;
}
