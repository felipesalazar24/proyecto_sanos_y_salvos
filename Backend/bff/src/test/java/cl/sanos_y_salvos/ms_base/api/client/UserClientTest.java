package cl.sanos_y_salvos.ms_base.api.client;

import cl.sanos_y_salvos.ms_base.api.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserClientTest {

    @Mock
    private RestTemplate restTemplate;

    private UserClient userClient;
    private final String baseUrl = "http://localhost:8080";
    private UserDTO testUser;

    @BeforeEach
    void setUp() {

        userClient = new UserClient(restTemplate, baseUrl);

        testUser = new UserDTO();
        testUser.setId(1L);
        testUser.setEmail("felipe@test.com");
    }

    @Test
    void getAll_Success() {
        UserDTO[] mockResponse = {testUser};
        when(restTemplate.getForObject(baseUrl + "/api/v1/users", UserDTO[].class))
                .thenReturn(mockResponse);

        List<UserDTO> result = userClient.getAll();

        assertFalse(result.isEmpty());
        assertEquals("felipe@test.com", result.get(0).getEmail());
    }

    @Test
    void getById_Success() {
        when(restTemplate.getForObject(baseUrl + "/api/v1/users/1", UserDTO.class))
                .thenReturn(testUser);

        UserDTO result = userClient.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getUserByEmail_Success() {
        when(restTemplate.getForObject(baseUrl + "/api/v1/users/email/felipe@test.com", UserDTO.class))
                .thenReturn(testUser);

        UserDTO result = userClient.getUserByEmail("felipe@test.com");

        assertNotNull(result);
        assertEquals("felipe@test.com", result.getEmail());
    }

    @Test
    void create_Success() {
        when(restTemplate.postForObject(baseUrl + "/api/v1/users", testUser, UserDTO.class))
                .thenReturn(testUser);

        UserDTO result = userClient.create(testUser);

        assertNotNull(result);
        assertEquals("felipe@test.com", result.getEmail());
    }

    @Test
    void update_Success() {
        doNothing().when(restTemplate).put(baseUrl + "/api/v1/users/1", testUser);

        userClient.update(1L, testUser);

        verify(restTemplate, times(1)).put(baseUrl + "/api/v1/users/1", testUser);
    }

    @Test
    void delete_Success() {
        doNothing().when(restTemplate).delete(baseUrl + "/api/v1/users/1");

        userClient.delete(1L);

        verify(restTemplate, times(1)).delete(baseUrl + "/api/v1/users/1");
    }
}