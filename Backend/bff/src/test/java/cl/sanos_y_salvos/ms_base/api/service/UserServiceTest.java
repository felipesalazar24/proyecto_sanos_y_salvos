package cl.sanos_y_salvos.ms_base.api.service;

import cl.sanos_y_salvos.ms_base.api.client.UserClient;
import cl.sanos_y_salvos.ms_base.api.dto.UserDTO;
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
class UserServiceTest {

    @Mock
    private UserClient userClient;

    @InjectMocks
    private UserService userService;

    private UserDTO testUser;

    @BeforeEach
    void setUp() {
        testUser = new UserDTO();
        testUser.setId(1L);
        testUser.setEmail("felipe@test.com");
    }

    @Test
    void findAll_Success() {
        when(userClient.getAll()).thenReturn(Arrays.asList(testUser));
        List<UserDTO> result = userService.findAll();
        assertFalse(result.isEmpty());
        verify(userClient, times(1)).getAll();
    }

    @Test
    void findById_Success() {
        when(userClient.getById(1L)).thenReturn(testUser);
        UserDTO result = userService.findById(1L);
        assertNotNull(result);
        verify(userClient, times(1)).getById(1L);
    }

    @Test
    void getUserByEmail_Success() {
        when(userClient.getUserByEmail("test@test.com")).thenReturn(testUser);
        UserDTO result = userService.getUserByEmail("test@test.com");
        assertNotNull(result);
        verify(userClient, times(1)).getUserByEmail("test@test.com");
    }

    @Test
    void save_Success() {
        when(userClient.create(testUser)).thenReturn(testUser);
        UserDTO result = userService.save(testUser);
        assertNotNull(result);
        verify(userClient, times(1)).create(testUser);
    }

    @Test
    void update_Success() {
        doNothing().when(userClient).update(1L, testUser);
        userService.update(1L, testUser);
        verify(userClient, times(1)).update(1L, testUser);
    }

    @Test
    void delete_Success() {
        doNothing().when(userClient).delete(1L);
        userService.delete(1L);
        verify(userClient, times(1)).delete(1L);
    }
}