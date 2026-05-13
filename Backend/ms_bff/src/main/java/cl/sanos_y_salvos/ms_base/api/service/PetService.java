package cl.sanos_y_salvos.ms_base.api.service;

import org.springframework.stereotype.Service;

import cl.sanos_y_salvos.ms_base.api.client.PetClient;
import cl.sanos_y_salvos.ms_base.api.dto.PetDTO;
import cl.sanos_y_salvos.ms_base.api.dto.PetTypeDTO;

import java.util.List;

@Service
public class PetService {
    
    private final PetClient petClient;

    public PetService(PetClient petClient) {
        this.petClient = petClient;
    }

    public List<PetDTO> findAllPets() { return petClient.getAllPets(); }
    
    public PetDTO savePet(PetDTO Pet) { return petClient.createPet(Pet); }

    public List<PetTypeDTO> findAllTypes() { return petClient.getAllTypes(); }
    
    public PetTypeDTO saveType(PetTypeDTO type) { return petClient.createType(type); }
}

