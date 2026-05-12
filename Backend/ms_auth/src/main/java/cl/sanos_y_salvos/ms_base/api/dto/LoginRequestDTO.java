package cl.sanos_y_salvos.ms_base.api.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    
    private Long id;
    private String email;
    private String password;
}
