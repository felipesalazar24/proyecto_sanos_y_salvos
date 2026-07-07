package cl.sanos_y_salvos.ms_base.api.controller;

import cl.sanos_y_salvos.ms_base.api.dto.PetTypeDTO;
import cl.sanos_y_salvos.ms_base.api.service.PetTypeService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pet-types")
public class PetTypeController {
    
    @Autowired
    private PetTypeService petTypeService;

    @GetMapping("")
    public ResponseEntity<List<PetTypeDTO>> getAllPetTypes() {
        List<PetTypeDTO> petTypes = petTypeService.getAllPetTypes();
        return new ResponseEntity<>(petTypes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetTypeDTO> getPetTypeById(@PathVariable Long id) {
        try {
            PetTypeDTO petType = petTypeService.getPetTypeById(id);
            return new ResponseEntity<>(petType, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<PetTypeDTO> createPetType(@RequestBody PetTypeDTO petTypeDto) {
        if(petTypeDto.getNameType() == null || petTypeDto.getNameType().isEmpty() ||
           petTypeDto.getBreed() == null || petTypeDto.getBreed().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        PetTypeDTO createdPetType = petTypeService.createPetType(petTypeDto);
        return new ResponseEntity<>(createdPetType, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetTypeDTO> updatePetType(@PathVariable Long id, @RequestBody PetTypeDTO petTypeDto) {
        try {
            PetTypeDTO updatedPetType = petTypeService.updatePetType(id, petTypeDto);
            return new ResponseEntity<>(updatedPetType, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetType(@PathVariable Long id) {
        try {
            petTypeService.deletePetType(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
