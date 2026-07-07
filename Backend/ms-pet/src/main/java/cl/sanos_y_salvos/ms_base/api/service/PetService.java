package cl.sanos_y_salvos.ms_base.api.service;

import cl.sanos_y_salvos.ms_base.api.dto.PetDTO;
import cl.sanos_y_salvos.ms_base.api.model.Pet;
import cl.sanos_y_salvos.ms_base.api.repository.PetRepository;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class PetService {
    
    @Autowired
    private PetRepository petRepository;

    public PetDTO getPetById(Long id) {
        Pet pet = petRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Mascota no encontrada con el id: " + id));
        return entityToDto(pet);
    }

    public List<PetDTO> getAllPets() {
        return petRepository.findAll().stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PetDTO createPet(PetDTO petDto) {
        Pet pet = dtoToEntity(petDto);
        Pet savedPet = petRepository.save(pet);
        return entityToDto(savedPet);
    }

    @Transactional
    public PetDTO updatePet(Long id, PetDTO petDto) {
        Pet existingPet = petRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Mascota no encontrada con el id: " + id));
        
        updateEntityFromDto(existingPet, petDto);
        
        Pet updatedPet = petRepository.save(existingPet);
        return entityToDto(updatedPet);
    }

    @Transactional
    public void deletePet(Long id) {
        if (petRepository.existsById(id)) {
            petRepository.deleteById(id);
        } else {
            throw new RuntimeException("Mascota no encontrada con el id: " + id);
        }
    }

    private Pet dtoToEntity(PetDTO dto) {
        Pet entity = new Pet();
        entity.setName(dto.getName());
        entity.setAgeCategory(dto.getAgeCategory());
        entity.setTypeId(dto.getTypeId());
        entity.setUserId(dto.getUserId());
        entity.setLastSeenLocation(dto.getLastSeenLocation());
        entity.setLastSeenDate(dto.getLastSeenDate());
        entity.setColor(dto.getColor());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    private PetDTO entityToDto(Pet entity) {
        PetDTO dto = new PetDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAgeCategory(entity.getAgeCategory());
        dto.setTypeId(entity.getTypeId());
        dto.setUserId(entity.getUserId());
        dto.setLastSeenLocation(entity.getLastSeenLocation());
        dto.setLastSeenDate(entity.getLastSeenDate());
        dto.setColor(entity.getColor());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    private void updateEntityFromDto(Pet entity, PetDTO dto) {
        entity.setName(dto.getName());
        entity.setAgeCategory(dto.getAgeCategory());
        entity.setTypeId(dto.getTypeId());
        entity.setUserId(dto.getUserId());
        entity.setLastSeenLocation(dto.getLastSeenLocation());
        entity.setLastSeenDate(dto.getLastSeenDate());
        entity.setColor(dto.getColor());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
    }

}
