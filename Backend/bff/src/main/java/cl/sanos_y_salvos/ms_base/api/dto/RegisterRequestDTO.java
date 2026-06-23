package cl.sanos_y_salvos.ms_base.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;    

public record RegisterRequestDTO(
    @NotBlank @Size(max = 20) String name,
    @NotBlank @Size(max = 20) String lastName,
    @NotBlank @Email @Size(max = 320) String email,
    @NotBlank @Size(min = 8, max = 72) String password,
    @jakarta.validation.constraints.Min(10000000) @jakarta.validation.constraints.Max(99999999) int phoneNumber,
    @NotBlank @Size(max = 100) String address,
    @jakarta.validation.constraints.Min(1000) @jakarta.validation.constraints.Max(9999) int addressNumber,
    @NotBlank @Size(max = 50)String city,
    @NotBlank @Size(max = 50)String country
) {
}