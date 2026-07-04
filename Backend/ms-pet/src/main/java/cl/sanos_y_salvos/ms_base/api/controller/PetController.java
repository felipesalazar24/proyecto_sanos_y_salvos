package cl.sanos_y_salvos.ms_base.api.controller;

import cl.sanos_y_salvos.ms_base.api.dto.PetDTO;
import cl.sanos_y_salvos.ms_base.api.service.PetService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping("")
    public ResponseEntity<List<PetDTO>> getAllPets() {
        List<PetDTO> pets = petService.getAllPets();
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> getPetById(@PathVariable Long id) {
        try {
            PetDTO pet = petService.getPetById(id);
            return new ResponseEntity<>(pet, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<PetDTO> createPet(@RequestBody PetDTO petDto) {
        if(petDto.getName() == null || petDto.getName().isEmpty() ||
           petDto.getAgeCategory() == null || petDto.getAgeCategory().isEmpty() ||
           petDto.getTypeId() == null || petDto.getTypeId() == 0 ||
           petDto.getLastSeenLocation() == null || petDto.getLastSeenLocation().isEmpty() ||
           petDto.getLastSeenDate() == null ||
           petDto.getColor() == null || petDto.getColor().isEmpty() ||
           petDto.getDescription() == null || petDto.getDescription().isEmpty() ||
           petDto.getStatus() == null || petDto.getStatus().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        PetDTO createdPet = petService.createPet(petDto);
        return new ResponseEntity<>(createdPet, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDTO> updatePet(@PathVariable Long id, @RequestBody PetDTO petDto) {
        try {
            PetDTO updatedPet = petService.updatePet(id, petDto);
            return new ResponseEntity<>(updatedPet, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        try {
            petService.deletePet(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
