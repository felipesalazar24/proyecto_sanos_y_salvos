package cl.sanos_y_salvos.ms_base.api.service;

import cl.sanos_y_salvos.ms_base.api.DTO.UserDto;
import cl.sanos_y_salvos.ms_base.api.DTO.AuthUserDTO;
import cl.sanos_y_salvos.ms_base.api.model.User;
import cl.sanos_y_salvos.ms_base.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserDto testUserDto;

    @BeforeEach
    void setUp() {

        testUser = User.builder()
                .id(1L)
                .name("Felipe")
                .lastName("Prueba")
                .email("felipe@test.com")
                .password("hashed_password_123")
                .phoneNumber(987654321)
                .address("Avenida Siempre Viva")
                .addressNumber(742)
                .city("Santiago")
                .country("Chile")
                .role("user")
                .build();

        testUserDto = new UserDto();
        testUserDto.setId(1L);
        testUserDto.setName("Felipe");
        testUserDto.setLastName("Prueba");
        testUserDto.setEmail("felipe@test.com");
        testUserDto.setPassword("plain_password");
        testUserDto.setPhoneNumber(987654321);
        testUserDto.setAddress("Avenida Siempre Viva");
        testUserDto.setAddressNumber(742);
        testUserDto.setCity("Santiago");
        testUserDto.setCountry("Chile");
        testUserDto.setRole("user");
    }

    @Test
    void getUserById_Success() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        UserDto result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());
        assertEquals(testUser.getName(), result.getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserById_ThrowsException_WhenNotFound() {

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserById(99L);
        });
        
        assertEquals("Usuario no encontrado con el id: 99", exception.getMessage());
        verify(userRepository, times(1)).findById(99L);
    }

    @Test
    void getAllUsers_Success() {

        when(userRepository.findAll()).thenReturn(Arrays.asList(testUser));

        List<UserDto> result = userService.getAllUsers();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Felipe", result.get(0).getName());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void createUser_Success() {

        when(passwordEncoder.encode("plain_password")).thenReturn("hashed_password_123");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        UserDto result = userService.createUser(testUserDto);

        assertNotNull(result);
        assertEquals("Felipe", result.getName());
        
        verify(passwordEncoder, times(1)).encode("plain_password");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser_Success_WithPasswordChange() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(passwordEncoder.encode(testUserDto.getPassword())).thenReturn("new_hashed_password");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        UserDto result = userService.updateUser(1L, testUserDto);

        assertNotNull(result);
        verify(passwordEncoder, times(1)).encode(testUserDto.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void deleteUser_Success() {

        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void getAuthInfoByEmail_Success() {

        when(userRepository.findByEmail("felipe@test.com")).thenReturn(Optional.of(testUser));

        AuthUserDTO result = userService.getAuthInfoByEmail("felipe@test.com");

        assertNotNull(result);
        assertEquals("felipe@test.com", result.getEmail());
        assertEquals("hashed_password_123", result.getPassword());
    }
}