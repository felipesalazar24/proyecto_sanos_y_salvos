package cl.sanos_y_salvos.ms_base.api.DTO;

import lombok.Data;

@Data
public class AuthUserDTO {
    
    private Long id;
    private String email;
    private String password;
}
