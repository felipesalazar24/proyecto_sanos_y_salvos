package cl.sanos_y_salvos.ms_base.api.controller;

import cl.sanos_y_salvos.ms_base.api.dto.UserDTO;
import cl.sanos_y_salvos.ms_base.api.service.UserService;
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
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDTO testUser;

    @BeforeEach
    void setUp() {
        testUser = new UserDTO();
        testUser.setId(1L);
        testUser.setName("Felipe");
        testUser.setEmail("felipe@test.com");
    }

    @Test
    void getUserProfile_Success() {
        when(userService.getUserByEmail("felipe@test.com")).thenReturn(testUser);

        ResponseEntity<UserDTO> response = userController.getUserProfile("felipe@test.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("felipe@test.com", response.getBody().getEmail());
    }

    @Test
    void getAll_Success() {
        when(userService.findAll()).thenReturn(Arrays.asList(testUser));

        ResponseEntity<List<UserDTO>> response = userController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getById_Success() {
        when(userService.findById(1L)).thenReturn(testUser);

        ResponseEntity<UserDTO> response = userController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void create_Success() {
        when(userService.save(any(UserDTO.class))).thenReturn(testUser);

        ResponseEntity<UserDTO> response = userController.create(testUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Felipe", response.getBody().getName());
    }

    @Test
    void update_Success() {
        doNothing().when(userService).update(1L, testUser);

        ResponseEntity<Void> response = userController.update(1L, testUser);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).update(1L, testUser);
    }

    @Test
    void delete_Success() {
        doNothing().when(userService).delete(1L);

        ResponseEntity<Void> response = userController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).delete(1L);
    }
}