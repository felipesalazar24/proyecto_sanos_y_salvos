package cl.sanos_y_salvos.ms_base.api.dto;

import java.time.Instant;
import java.util.Map;

public record ValidateResponse(
        boolean valid,
        String subject,
        String issuer,
        Instant expiresAt,
        Map<String, Object> claims
) {
}