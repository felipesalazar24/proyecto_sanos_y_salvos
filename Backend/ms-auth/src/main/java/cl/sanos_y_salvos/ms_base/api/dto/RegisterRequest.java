package cl.sanos_y_salvos.ms_base.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank @Size(max = 320) @Email String email,
    @NotBlank @Size(min = 6, max = 72) String password
) {
}