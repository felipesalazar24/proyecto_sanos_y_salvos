package cl.sanos_y_salvos.ms_base.api.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import cl.sanos_y_salvos.ms_base.api.dto.UserDTO;
import java.util.Arrays;
import java.util.List;

@Component
public class UserClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public UserClient(RestTemplate restTemplate, @Value("${endpoints.ms-users}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl + "/api/v1/users";
    }

    public List<UserDTO> getAll() {
        UserDTO[] response = restTemplate.getForObject(baseUrl, UserDTO[].class);
        return Arrays.asList(response != null ? response : new UserDTO[0]);
    }

    public UserDTO getById(Long id) {
        return restTemplate.getForObject(baseUrl + "/" + id, UserDTO.class);
    }

    public UserDTO getUserByEmail(String email) {
        return restTemplate.getForObject(baseUrl + "/email/" + email, UserDTO.class);
    }

    public UserDTO getByEmailInfo(String email) {
        return restTemplate.getForObject(baseUrl + "/auth-info?email=" + email, UserDTO.class);
    }

    public UserDTO create(UserDTO user) {
        return restTemplate.postForObject(baseUrl, user, UserDTO.class);
    }

    public void update(Long id, UserDTO user) {
        restTemplate.put(baseUrl + "/" + id, user);
    }

    public void delete(Long id) {
        restTemplate.delete(baseUrl + "/" + id);
    }
}