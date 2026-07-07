package cl.sanos_y_salvos.ms_base.api.DTO;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
public class UserDto {
    
    private Long id;
    private String name;
    private String lastName;
    @NotBlank(message = "El correo es obligatorio")
    @Pattern(
        regexp = "^[a-zA-Z0-9._%+-]+@(gmail\\.com|gmail\\.cl|duocuc\\.cl)$",
        message = "El correo debe pertenecer a los dominios permitidos: @gmail.com, @gmail.cl o @duocuc.cl"
    )
    private String email;
    private String password;
    private int phoneNumber;
    private String address;
    private int addressNumber;
    private String city;
    private String country;
    private String role;
}
