package cl.sanos_y_salvos.ms_base.api.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String password; // Solo se usa para el Create/Update
    private int phoneNumber;
    private String address;
    private int addressNumber;
    private String city;
    private String country;
    private String role;
}