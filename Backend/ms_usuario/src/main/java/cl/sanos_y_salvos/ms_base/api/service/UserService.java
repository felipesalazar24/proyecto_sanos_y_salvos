package main.java.cl.sanos_y_salvos.ms_base.api.service;

import main.java.cl.sanos_y_salvos.ms_base.api.DTO.UserDto;
import main.java.cl.sanos_y_salvos.ms_base.api.repository.UserRepository;

import java.lang.foreign.Linker.Option;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDto getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado con el id: " + id));
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll();    
    }

    @Transactional
    public UserDto createUser(UserDto userDto) {
        return userRepository.save(userDto);
    }

    @transactional
    public UserDto updateUser(Long id, UserDto userDto) {
        Optional<UserDto> optionalUser = userRepository.findById(id);
        if (optionalUser.present()) {
            
        UserDto existingUser = optionalUser.get();
        existingUser.setName(userDto.getName());
        existingUser.setLastName(userDto.getLastName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setPassword(userDto.getPassword());
        existingUser.setPhoneNumber(userDto.getPhoneNumber());
        existingUser.setAddress(userDto.getAddress());
        existingUser.setAddressNumber(userDto.getAddressNumber());
        existingUser.setCity(userDto.getCity());
        existingUser.setCountry(userDto.getCountry());
        existingUser.setRole(userDto.getRole());

        return userRepository.save(existingUser);   
        } else {
             throw new RuntimeException("Usuario no encontrado con el id: " + id);
             return null;
        }
    }

    @Transactional
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuario no encontrado con el id: " + id);
        }
    }

    
}
