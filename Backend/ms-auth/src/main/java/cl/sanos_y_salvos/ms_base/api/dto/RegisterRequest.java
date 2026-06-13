package cl.sanos_y_salvos.ms_base.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank @Size(max = 20) String name,
    @NotBlank @Size(max = 20) String lastName,
    @NotBlank @Email @Size(max = 320) String email,
    @NotBlank @Size(min = 8, max = 72) String password,
    @NotBlank @Size(min = 8, max = 8) int phoneNumber,
    @NotBlank @Size(max = 100) String address,
    @NotBlank @Size(min =8 ,max =8) int addressNumber,
    @NotBlank @Size(max = 50)String city,
    @NotBlank @Size(max = 50)String country
) {
}


