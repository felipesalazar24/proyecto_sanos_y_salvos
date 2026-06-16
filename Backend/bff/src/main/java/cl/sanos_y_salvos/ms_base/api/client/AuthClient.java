package cl.sanos_y_salvos.ms_base.api.client;

import cl.sanos_y_salvos.ms_base.api.dto.LoginRequestDTO;
import cl.sanos_y_salvos.ms_base.api.dto.AuthResponseDTO;
import cl.sanos_y_salvos.ms_base.api.dto.RegisterRequestDTO;
import cl.sanos_y_salvos.ms_base.api.dto.ValidateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ms-auth", url = "${endpoints.ms-auth}")
public interface AuthClient {

    @PostMapping("/api/v1/auth/login")
    AuthResponseDTO login(@RequestBody LoginRequestDTO loginRequest);

    @PostMapping("/api/v1/auth/register")
    AuthResponseDTO register(@RequestBody RegisterRequestDTO registerRequest);

    @GetMapping("/api/v1/auth/validate")
    ValidateResponse validateToken(@RequestHeader("Authorization") String token);
}