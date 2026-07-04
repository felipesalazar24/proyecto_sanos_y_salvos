package cl.sanos_y_salvos.ms_base.api.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import cl.sanos_y_salvos.ms_base.api.dto.PetDTO;
import cl.sanos_y_salvos.ms_base.api.dto.PetTypeDTO;
import java.util.Arrays;
import java.util.List;

@Component
public class PetClient {
    
    private final RestTemplate restTemplate;
    
    private final String baseUrl;
    private final String typeUrl;

    public PetClient(RestTemplate restTemplate, @Value("${endpoints.ms-pets}") String msPetsUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = msPetsUrl + "/api/v1/pets";
        this.typeUrl = msPetsUrl + "/api/v1/pet-types";
    }
    
    // --- Métodos de Mascota ---
    public List<PetDTO> getAllPets() {
        PetDTO[] response = restTemplate.getForObject(baseUrl, PetDTO[].class);
        return Arrays.asList(response != null ? response : new PetDTO[0]);
    }

    public PetDTO getPetById(Long id) {
        return restTemplate.getForObject(baseUrl + "/" + id, PetDTO.class);
    }

    public PetDTO createPet(PetDTO pet) {
        return restTemplate.postForObject(baseUrl, pet, PetDTO.class);
    }

    // --- Métodos de Tipo Mascota ---
    public List<PetTypeDTO> getAllPetTypes() {
        PetTypeDTO[] response = restTemplate.getForObject(typeUrl, PetTypeDTO[].class);
        return Arrays.asList(response != null ? response : new PetTypeDTO[0]);
    }

    public PetTypeDTO getPetTypeById(Long id) {
        return restTemplate.getForObject(typeUrl + "/" + id, PetTypeDTO.class);
    }

    public PetTypeDTO createType(PetTypeDTO type) {
        return restTemplate.postForObject(typeUrl, type, PetTypeDTO.class);
    }
}