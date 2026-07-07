package cl.sanos_y_salvos.ms_base.api.controller;

import cl.sanos_y_salvos.ms_base.api.dto.PetDTO;
import cl.sanos_y_salvos.ms_base.api.dto.PetTypeDTO;
import cl.sanos_y_salvos.ms_base.api.service.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    @Mock
    private PetService petService;

    @InjectMocks
    private PetController petController;

    private PetDTO testPet;
    private PetTypeDTO testPetType;

    @BeforeEach
    void setUp() {
        testPet = new PetDTO();
        testPet.setId(1L);
        testPet.setName("Cachupin");

        testPetType = new PetTypeDTO();
        testPetType.setId(1L);
        testPetType.setNameType("Gato");
    }

    @Test
    void getAllPets_Success() {
        when(petService.findAllPets()).thenReturn(Arrays.asList(testPet));

        ResponseEntity<List<PetDTO>> response = petController.getAllPets();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void createPet_Success() {
        when(petService.savePet(any(PetDTO.class))).thenReturn(testPet);

        ResponseEntity<PetDTO> response = petController.createPet(testPet);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Cachupin", response.getBody().getName());
        verify(petService, times(1)).savePet(testPet);
    }

    @Test
    void getAllPetTypes_Success() {
        when(petService.findAllTypes()).thenReturn(Arrays.asList(testPetType));

        ResponseEntity<List<PetTypeDTO>> response = petController.getAllPetTypes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getPetTypeById_Success() {
        when(petService.getPetTypeById(1L)).thenReturn(testPetType);

        ResponseEntity<PetTypeDTO> response = petController.getPetTypeById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Gato", response.getBody().getNameType());
    }

    @Test
    void createType_Success() {
        when(petService.saveType(any(PetTypeDTO.class))).thenReturn(testPetType);

        ResponseEntity<PetTypeDTO> response = petController.createType(testPetType);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Gato", response.getBody().getNameType());
        verify(petService, times(1)).saveType(testPetType);
    }
}