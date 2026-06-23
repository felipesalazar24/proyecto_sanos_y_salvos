package cl.sanos_y_salvos.ms_base.api.controller; 

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.sanos_y_salvos.ms_base.api.dto.LoginRequestDTO;
import cl.sanos_y_salvos.ms_base.api.dto.NotificationDTO;
import cl.sanos_y_salvos.ms_base.api.dto.AuthResponseDTO;
import cl.sanos_y_salvos.ms_base.api.dto.NotificationDTO;
import cl.sanos_y_salvos.ms_base.api.dto.PetDTO;
import cl.sanos_y_salvos.ms_base.api.dto.PetTypeDTO;
import cl.sanos_y_salvos.ms_base.api.dto.RegisterRequestDTO;
import cl.sanos_y_salvos.ms_base.api.dto.UserDTO;
import cl.sanos_y_salvos.ms_base.api.service.AuthService;
import cl.sanos_y_salvos.ms_base.api.service.PetService;
import cl.sanos_y_salvos.ms_base.api.service.UserService;
import cl.sanos_y_salvos.ms_base.api.service.NotificationService;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/bff/web")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
public class BffController {

    private final AuthService authService;
    private final UserService userService;
    private final PetService petService;
    private final NotificationService notificationService; // 🚀 Inyección del servicio de notificaciones

    public BffController(AuthService authService, UserService userService, PetService petService, NotificationService notificationService) {
        this.authService = authService;
        this.userService = userService;
        this.petService = petService;
        this.notificationService = notificationService;
    }

    // --- AUTH ---
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        AuthResponseDTO response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequest) {
        AuthResponseDTO response = authService.register(registerRequest);
        return ResponseEntity.ok(response);
    }

    // --- USERS ---
    @GetMapping("/users/profile/auth-info")
    public ResponseEntity<UserDTO> getAuthInfo(@RequestParam String email) {
        return ResponseEntity.ok(userService.findByEmailInfo(email));
    }

    @GetMapping("/users/profile")
    public ResponseEntity<UserDTO> getUserProfile(@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping("/users") 
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/users/{id}") 
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UserDTO user) {
        userService.update(id, user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- ENDPOINTS PETS ---
    @GetMapping("/pets")
    public ResponseEntity<List<PetDTO>> getAllPets() {
        return ResponseEntity.ok(petService.findAllPets());
    }

    @PostMapping("/pets")
    public ResponseEntity<PetDTO> createPet(@RequestBody PetDTO pet) {
        PetDTO savedPet = petService.savePet(pet);
        
        System.out.println("[MS-NOTIFICATION MOCK] -> Enviando alerta masiva de mascota perdida: " + savedPet.getName());
        
        return ResponseEntity.ok(savedPet);
    }

    // --- ENDPOINTS PET TYPES ---
    @GetMapping("/pet-types")
    public ResponseEntity<List<PetTypeDTO>> getAllPetTypes() {
        return ResponseEntity.ok(petService.findAllTypes()); 
    }

    @GetMapping("/pet-types/{id}")
    public ResponseEntity<PetTypeDTO> getPetTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(petService.getPetTypeById(id));
    }

    @PostMapping("/pet-types")
    public ResponseEntity<PetTypeDTO> createType(@RequestBody PetTypeDTO type) {
        return ResponseEntity.ok(petService.saveType(type));
    }

    @PostMapping("/notifications")
    public ResponseEntity<Map<String, String>> sendManualNotification(@RequestBody Map<String, Object> payload) {
        System.out.println("[MS-NOTIFICATION MOCK] -> Notificación manual procesada con éxito. Payload: " + payload);
        return ResponseEntity.ok(Map.of(
            "status", "SENT",
            "message", "Alerta distribuida correctamente a los usuarios del sector."
        ));
    }

    // --- NOTIFICATIONS ---
    @PostMapping("/notifications")
    public ResponseEntity<NotificationDTO> sendManualNotification(@RequestBody NotificationDTO notificationDTO) {
        NotificationDTO response = notificationService.dispatchNotification(notificationDTO);
        return ResponseEntity.ok(response);
    }
}