package cl.sanos_y_salvos.ms_base.api.client;

import cl.sanos_y_salvos.ms_base.api.dto.PetDTO;
import cl.sanos_y_salvos.ms_base.api.dto.PetTypeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PetClientTest {

    @Mock
    private RestTemplate restTemplate;

    private PetClient petClient;
    private final String msPetsUrl = "http://localhost:8081";
    private final String baseUrl = msPetsUrl + "/api/v1/pets";
    private final String typeUrl = msPetsUrl + "/api/v1/pet-types";

    private PetDTO testPet;
    private PetTypeDTO testPetType;

    @BeforeEach
    void setUp() {
        petClient = new PetClient(restTemplate, msPetsUrl);

        testPet = new PetDTO();
        testPet.setId(1L);
        testPet.setName("Cachupin");

        testPetType = new PetTypeDTO();
        testPetType.setId(1L);
        testPetType.setNameType("Perro");
        testPetType.setBreed("Kiltro");
    }

    @Test
    void getAllPets_Success() {
        PetDTO[] mockResponse = {testPet};
        when(restTemplate.getForObject(baseUrl, PetDTO[].class)).thenReturn(mockResponse);

        List<PetDTO> result = petClient.getAllPets();

        assertFalse(result.isEmpty());
        assertEquals("Cachupin", result.get(0).getName());
    }

    @Test
    void getPetById_Success() {
        when(restTemplate.getForObject(baseUrl + "/1", PetDTO.class)).thenReturn(testPet);

        PetDTO result = petClient.getPetById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void createPet_Success() {
        when(restTemplate.postForObject(baseUrl, testPet, PetDTO.class)).thenReturn(testPet);

        PetDTO result = petClient.createPet(testPet);

        assertNotNull(result);
        assertEquals("Cachupin", result.getName());
    }

    @Test
    void getAllPetTypes_Success() {
        PetTypeDTO[] mockResponse = {testPetType};
        when(restTemplate.getForObject(typeUrl, PetTypeDTO[].class)).thenReturn(mockResponse);

        List<PetTypeDTO> result = petClient.getAllPetTypes();

        assertFalse(result.isEmpty());
        assertEquals("Perro", result.get(0).getNameType());
    }

    @Test
    void getPetTypeById_Success() {
        when(restTemplate.getForObject(typeUrl + "/1", PetTypeDTO.class)).thenReturn(testPetType);

        PetTypeDTO result = petClient.getPetTypeById(1L);

        assertNotNull(result);
        assertEquals("Kiltro", result.getBreed());
    }

    @Test
    void createType_Success() {
        when(restTemplate.postForObject(typeUrl, testPetType, PetTypeDTO.class)).thenReturn(testPetType);

        PetTypeDTO result = petClient.createType(testPetType);

        assertNotNull(result);
        assertEquals("Perro", result.getNameType());
    }
}