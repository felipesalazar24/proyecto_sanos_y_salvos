package cl.sanos_y_salvos.ms_base.api.service;

import cl.sanos_y_salvos.ms_base.api.dto.PetDTO;
import cl.sanos_y_salvos.ms_base.api.model.Pet;
import cl.sanos_y_salvos.ms_base.api.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetService petService;

    private Pet testPet;
    private PetDTO testPetDTO;
    private Date now;

    @BeforeEach
    void setUp() {
        now = new Date();

        testPet = Pet.builder()
                .id(1L)
                .name("Cachupin")
                .ageCategory("Adulto")
                .typeId(1L)
                .userId(1L)
                .lastSeenLocation("Plaza Central")
                .lastSeenDate(now)
                .color("Negro")
                .description("Perro amigable")
                .status("Perdido")
                .build();

        testPetDTO = new PetDTO();
        testPetDTO.setId(1L);
        testPetDTO.setName("Cachupin");
        testPetDTO.setAgeCategory("Adulto");
        testPetDTO.setTypeId(1L);
        testPetDTO.setUserId(1L);
        testPetDTO.setLastSeenLocation("Plaza Central");
        testPetDTO.setLastSeenDate(now);
        testPetDTO.setColor("Negro");
        testPetDTO.setDescription("Perro amigable");
        testPetDTO.setStatus("Perdido");
    }

    @Test
    void getPetById_Success() {
        when(petRepository.findById(1L)).thenReturn(Optional.of(testPet));

        PetDTO result = petService.getPetById(1L);

        assertNotNull(result);
        assertEquals("Cachupin", result.getName());
        assertEquals("Perdido", result.getStatus());
        verify(petRepository, times(1)).findById(1L);
    }

    @Test
    void getPetById_ThrowsException_WhenNotFound() {
        when(petRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> petService.getPetById(99L));
        
        assertEquals("Mascota no encontrada con el id: 99", exception.getMessage());
    }

    @Test
    void getAllPets_Success() {
        when(petRepository.findAll()).thenReturn(Arrays.asList(testPet));

        List<PetDTO> result = petService.getAllPets();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Cachupin", result.get(0).getName());
    }

    @Test
    void createPet_Success() {
        when(petRepository.save(any(Pet.class))).thenReturn(testPet);

        PetDTO result = petService.createPet(testPetDTO);

        assertNotNull(result);
        assertEquals("Cachupin", result.getName());
        verify(petRepository, times(1)).save(any(Pet.class));
    }

    @Test
    void updatePet_Success() {
        when(petRepository.findById(1L)).thenReturn(Optional.of(testPet));
        when(petRepository.save(any(Pet.class))).thenReturn(testPet);

        PetDTO result = petService.updatePet(1L, testPetDTO);

        assertNotNull(result);
        assertEquals("Adulto", result.getAgeCategory());
        verify(petRepository, times(1)).save(any(Pet.class));
    }

    @Test
    void deletePet_Success() {
        when(petRepository.existsById(1L)).thenReturn(true);
        doNothing().when(petRepository).deleteById(1L);

        petService.deletePet(1L);

        verify(petRepository, times(1)).deleteById(1L);
    }
}