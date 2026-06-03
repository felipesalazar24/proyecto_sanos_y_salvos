package cl.sanos_y_salvos.ms_base.api.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import cl.sanos_y_salvos.ms_base.api.dto.PetDTO;
import cl.sanos_y_salvos.ms_base.api.dto.PetTypeDTO;

import java.util.Arrays;
import java.util.List;

@Component
public class PetClient {
    
    private final RestTemplate restTemplate;
    private final String BASE_URL = "http://ms-mascota:8082/api/v1/pets";
    private final String TIPO_URL = "http://ms-mascota:8082/api/v1/pet-types";

    public PetClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // --- Métodos de Mascota ---
    public List<PetDTO> getAllPets() {
        PetDTO[] response = restTemplate.getForObject(BASE_URL, PetDTO[].class);
        return Arrays.asList(response != null ? response : new PetDTO[0]);
    }

    public PetDTO getPetById(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, PetDTO.class);
    }

    public PetDTO createPet(PetDTO pet) {
        return restTemplate.postForObject(BASE_URL, pet, PetDTO.class);
    }

    // --- Métodos de Tipo Mascota ---
    public PetTypeDTO getPetTypeById(Long id) {
        return restTemplate.getForObject(TIPO_URL + "/" + id, PetTypeDTO.class);
    }

    public PetTypeDTO createType(PetTypeDTO type) {
        return restTemplate.postForObject(TIPO_URL, type, PetTypeDTO.class);
    }
}
