package cl.sanos_y_salvos.ms_base.api.DTO;

import lombok.Data;

@Data
public class UserDto {
    
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private int phoneNumber;
    private String address;
    private int addressNumber;
    private String city;
    private String country;
    private String role;
}
