package cl.sanos_y_salvos.ms_base.api.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import cl.sanos_y_salvos.ms_base.api.dto.UserDTO;

import java.util.Arrays;
import java.util.List;

@Component
public class UserClient {

    private final RestTemplate restTemplate;
    private final String BASE_URL = "http://ms-usuarios:8081/api/v1/users";

    public UserClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<UserDTO> getAll() {
        UserDTO[] response = restTemplate.getForObject(BASE_URL, UserDTO[].class);
        return Arrays.asList(response != null ? response : new UserDTO[0]);
    }

    public UserDTO getById(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, UserDTO.class);
    }

    public UserDTO create(UserDTO user) {
        return restTemplate.postForObject(BASE_URL, user, UserDTO.class);
    }

    public void update(Long id, UserDTO user) {
        restTemplate.put(BASE_URL + "/" + id, user);
    }

    public void delete(Long id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }
}