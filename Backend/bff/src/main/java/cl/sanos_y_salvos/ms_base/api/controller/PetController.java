package cl.sanos_y_salvos.ms_base.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.sanos_y_salvos.ms_base.api.dto.PetDTO;
import cl.sanos_y_salvos.ms_base.api.dto.PetTypeDTO;
import cl.sanos_y_salvos.ms_base.api.service.PetService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bff/web/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public ResponseEntity<List<PetDTO>> getAllPets() {
        return ResponseEntity.ok(petService.findAllPets());
    }

    @PostMapping
    public ResponseEntity<PetDTO> createPet(@RequestBody PetDTO pet) {
        PetDTO savedPet = petService.savePet(pet);
        
        System.out.println("[MS-NOTIFICATION MOCK] -> Enviando alerta masiva de mascota perdida: " + savedPet.getName());
        
        return ResponseEntity.ok(savedPet);
    }

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
}
