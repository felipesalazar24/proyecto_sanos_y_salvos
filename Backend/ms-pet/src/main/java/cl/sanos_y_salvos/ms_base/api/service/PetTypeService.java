package cl.sanos_y_salvos.ms_base.api.service;

import cl.sanos_y_salvos.ms_base.api.dto.PetTypeDTO;
import cl.sanos_y_salvos.ms_base.api.model.PetType;
import cl.sanos_y_salvos.ms_base.api.repository.PetTypeRepository;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class PetTypeService {

    @Autowired
    private PetTypeRepository petTypeRepository;

    public PetTypeDTO getPetTypeById(Long id) {
        PetType petType = petTypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tipo de mascota no encontrado con el id: " + id));
        return entityToDto(petType);
    }

    public List<PetTypeDTO> getAllPetTypes() {
        return petTypeRepository.findAll().stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PetTypeDTO createPetType(PetTypeDTO petTypeDto) {
        PetType petType = dtoToEntity(petTypeDto);
        PetType savedPetType = petTypeRepository.save(petType);
        return entityToDto(savedPetType);
    }

    @Transactional
    public PetTypeDTO updatePetType(Long id, PetTypeDTO petTypeDto) {
        PetType existingPetType = petTypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tipo de mascota no encontrado con el id: " + id));
        
        updateEntityFromDto(existingPetType, petTypeDto);
        
        PetType updatedPetType = petTypeRepository.save(existingPetType);
        return entityToDto(updatedPetType);
    }

    @Transactional
    public void deletePetType(Long id) {
        if (petTypeRepository.existsById(id)) {
            petTypeRepository.deleteById(id);
        } else {
            throw new RuntimeException("Tipo de mascota no encontrado con el id: " + id);
        }
    }

    private PetType dtoToEntity(PetTypeDTO dto) {
        PetType entity = new PetType();
        entity.setNameType(dto.getNameType());
        entity.setBreed(dto.getBreed());
        return entity;
    }

    private void updateEntityFromDto(PetType entity, PetTypeDTO dto) {
        entity.setNameType(dto.getNameType());
        entity.setBreed(dto.getBreed());
    }

    private PetTypeDTO entityToDto(PetType entity) {
        PetTypeDTO dto = new PetTypeDTO();
        dto.setId(entity.getId());
        dto.setNameType(entity.getNameType());
        dto.setBreed(entity.getBreed());
        return dto;
    }
    
}
