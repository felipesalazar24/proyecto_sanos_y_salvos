package cl.sanos_y_salvos.ms_base.api.dto;

public record AuthResponseDTO(
    String accessToken,
    String tokkenType,
    long expiresIn
) {
} 