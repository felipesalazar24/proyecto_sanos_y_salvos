package cl.sanos_y_salvos.ms_base.api.service;

import cl.sanos_y_salvos.ms_base.api.dto.PetTypeDTO;
import cl.sanos_y_salvos.ms_base.api.model.PetType;
import cl.sanos_y_salvos.ms_base.api.repository.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetTypeServiceTest {

    @Mock
    private PetTypeRepository petTypeRepository;

    @InjectMocks
    private PetTypeService petTypeService;

    private PetType testPetType;
    private PetTypeDTO testPetTypeDTO;

    @BeforeEach
    void setUp() {
        testPetType = PetType.builder()
                .id(1L)
                .nameType("Perro")
                .breed("Kiltro")
                .build();

        testPetTypeDTO = new PetTypeDTO();
        testPetTypeDTO.setId(1L);
        testPetTypeDTO.setNameType("Perro");
        testPetTypeDTO.setBreed("Kiltro");
    }

    @Test
    void getPetTypeById_Success() {
        when(petTypeRepository.findById(1L)).thenReturn(Optional.of(testPetType));

        PetTypeDTO result = petTypeService.getPetTypeById(1L);

        assertNotNull(result);
        assertEquals("Perro", result.getNameType());
        verify(petTypeRepository, times(1)).findById(1L);
    }

    @Test
    void getPetTypeById_ThrowsException_WhenNotFound() {
        when(petTypeRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> petTypeService.getPetTypeById(99L));
        
        assertEquals("Tipo de mascota no encontrado con el id: 99", exception.getMessage());
    }

    @Test
    void getAllPetTypes_Success() {
        when(petTypeRepository.findAll()).thenReturn(Arrays.asList(testPetType));

        List<PetTypeDTO> result = petTypeService.getAllPetTypes();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Perro", result.get(0).getNameType());
    }

    @Test
    void createPetType_Success() {
        when(petTypeRepository.save(any(PetType.class))).thenReturn(testPetType);

        PetTypeDTO result = petTypeService.createPetType(testPetTypeDTO);

        assertNotNull(result);
        assertEquals("Perro", result.getNameType());
        verify(petTypeRepository, times(1)).save(any(PetType.class));
    }

    @Test
    void updatePetType_Success() {
        when(petTypeRepository.findById(1L)).thenReturn(Optional.of(testPetType));
        when(petTypeRepository.save(any(PetType.class))).thenReturn(testPetType);

        PetTypeDTO result = petTypeService.updatePetType(1L, testPetTypeDTO);

        assertNotNull(result);
        assertEquals("Kiltro", result.getBreed());
        verify(petTypeRepository, times(1)).save(any(PetType.class));
    }

    @Test
    void deletePetType_Success() {
        when(petTypeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(petTypeRepository).deleteById(1L);

        petTypeService.deletePetType(1L);

        verify(petTypeRepository, times(1)).deleteById(1L);
    }
}