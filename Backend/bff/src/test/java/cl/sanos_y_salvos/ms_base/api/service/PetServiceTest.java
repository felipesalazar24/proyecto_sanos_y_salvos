package cl.sanos_y_salvos.ms_base.api.service;

import cl.sanos_y_salvos.ms_base.api.client.PetClient;
import cl.sanos_y_salvos.ms_base.api.dto.PetDTO;
import cl.sanos_y_salvos.ms_base.api.dto.PetTypeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    private PetClient petClient;

    @InjectMocks
    private PetService petService;

    private PetDTO testPet;
    private PetTypeDTO testPetType;

    @BeforeEach
    void setUp() {
        testPet = new PetDTO();
        testPet.setId(1L);
        testPet.setName("Cachupin");

        testPetType = new PetTypeDTO();
        testPetType.setId(1L);
        testPetType.setNameType("Perro");
    }

    @Test
    void findAllPets_Success() {
        when(petClient.getAllPets()).thenReturn(Arrays.asList(testPet));
        List<PetDTO> result = petService.findAllPets();
        assertFalse(result.isEmpty());
        verify(petClient, times(1)).getAllPets();
    }

    @Test
    void savePet_Success() {
        when(petClient.createPet(testPet)).thenReturn(testPet);
        PetDTO result = petService.savePet(testPet);
        assertNotNull(result);
        verify(petClient, times(1)).createPet(testPet);
    }

    @Test
    void findAllTypes_Success() {
        when(petClient.getAllPetTypes()).thenReturn(Arrays.asList(testPetType));
        List<PetTypeDTO> result = petService.findAllTypes();
        assertFalse(result.isEmpty());
        verify(petClient, times(1)).getAllPetTypes();
    }

    @Test
    void getPetTypeById_Success() {
        when(petClient.getPetTypeById(1L)).thenReturn(testPetType);
        PetTypeDTO result = petService.getPetTypeById(1L);
        assertNotNull(result);
        verify(petClient, times(1)).getPetTypeById(1L);
    }

    @Test
    void saveType_Success() {
        when(petClient.createType(testPetType)).thenReturn(testPetType);
        PetTypeDTO result = petService.saveType(testPetType);
        assertNotNull(result);
        verify(petClient, times(1)).createType(testPetType);
    }
}