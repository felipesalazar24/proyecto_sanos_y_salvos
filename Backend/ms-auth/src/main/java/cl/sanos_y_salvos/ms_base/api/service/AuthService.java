package cl.sanos_y_salvos.ms_base.api.service;

import cl.sanos_y_salvos.ms_base.api.controller.AuthController;
import cl.sanos_y_salvos.ms_base.api.dto.LoginRequestDTO;
import cl.sanos_y_salvos.ms_base.api.dto.RegisterRequest;
import cl.sanos_y_salvos.ms_base.api.dto.AuthResponseDTO;
import cl.sanos_y_salvos.ms_base.api.model.UserAccount;
import cl.sanos_y_salvos.ms_base.api.repository.AuthRepository;
import java.time.Instant;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.annotation.Lazy;

@Service
public class AuthService {

    private final AuthController authController;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
        AuthRepository authRepository,
        PasswordEncoder passwordEncoder,
        JwtService jwtService,@Lazy AuthController authController
    ) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authController = authController;
    }
    

    @Transactional
    public void register(RegisterRequest request) {
        if (authRepository.existsByEmailIgnoreCase(request.email())) {
            throw new IllegalArgumentException("Email already in use");
        }

        UserAccount user = new UserAccount();
        user.setName(request.name().trim());
        user.setLastName(request.lastName().trim());
        user.setEmail(request.email().trim().toLowerCase());
        user.setPassword(passwordEncoder.encode(request.password().trim()));
        user.setPhoneNumber(request.phoneNumber());
        user.setAddress(request.address().trim());
        user.setAddressNumber(request.addressNumber());
        user.setCity(request.city());
        user.setCountry(request.country());
        user.setRole("user");
        user.setEnabled(true);
        user.setCreatedAt(Instant.now());

        authRepository.save(user);
    }
    
    @Transactional(readOnly = true)
    public AuthResponseDTO login(LoginRequestDTO request) {
        UserAccount user = authRepository.findByEmailIgnoreCase(request.email())
            .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!Boolean.TRUE.equals(user.getEnabled()) || !passwordEncoder.matches(request.password().trim(), user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        String token = jwtService.generateAccessToken(user);
        return new AuthResponseDTO(token, "Bearer", jwtService.getAccessTokenTtlSeconds());
    }
}