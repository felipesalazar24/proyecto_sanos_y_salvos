package cl.sanos_y_salvos.ms_base.api.controller; 

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.sanos_y_salvos.ms_base.api.dto.LoginRequestDTO;
import cl.sanos_y_salvos.ms_base.api.dto.AuthResponseDTO;
import cl.sanos_y_salvos.ms_base.api.dto.PetDTO;
import cl.sanos_y_salvos.ms_base.api.dto.PetTypeDTO;
import cl.sanos_y_salvos.ms_base.api.dto.UserDTO;
import cl.sanos_y_salvos.ms_base.api.service.AuthService;
import cl.sanos_y_salvos.ms_base.api.service.PetService;
import cl.sanos_y_salvos.ms_base.api.service.UserService;
import java.util.List;


@RestController
@RequestMapping("/api/v1/bff/web")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
public class BffController {

    private final AuthService authService;
    private final UserService userService;
    private final PetService petService;

    public BffController(AuthService authService, UserService userService, PetService petService) {
        this.authService = authService;
        this.userService = userService;
        this.petService = petService;
    }

    // --- AUTH ---
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest){
        try {
            AuthResponseDTO response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Login failed: " + e.getMessage());
        }
    }


    // --- USUARIOS ---
    
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

    // --- ENDPOINTS MASCOTAS ---
    @GetMapping("/pets")
    public ResponseEntity<List<PetDTO>> getAllPets() {
        return ResponseEntity.ok(petService.findAllPets());
    }

    @PostMapping("/pets")
    public ResponseEntity<PetDTO> createPet(@RequestBody PetDTO pet) {
        return ResponseEntity.ok(petService.savePet(pet));
    }

    // --- ENDPOINTS TIPO MASCOTA ---
    @GetMapping("/pet-types/{id}")
    public ResponseEntity<PetTypeDTO> getPetTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(petService.getPetTypeById(id));
    }

    @PostMapping("/pet-types")
    public ResponseEntity<PetTypeDTO> createType(@RequestBody PetTypeDTO type) {
        return ResponseEntity.ok(petService.saveType(type));
    }
}