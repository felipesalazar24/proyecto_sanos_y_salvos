package cl.sanos_y_salvos.ms_base.api.service;

import org.springframework.stereotype.Service;

import cl.sanos_y_salvos.ms_base.api.client.UserClient;
import cl.sanos_y_salvos.ms_base.api.dto.UserDTO;

import java.util.List;

@Service
public class UserService {

    private final UserClient userClient;

    public UserService(UserClient userClient) {
        this.userClient = userClient;
    }

    public List<UserDTO> findAll() { return userClient.getAll(); }
    
    public UserDTO findById(Long id) { return userClient.getById(id); }

    public UserDTO findByEmail(String email) { return userClient.getByEmail(email); }
    
    public UserDTO save(UserDTO user) { return userClient.create(user); }
    
    public void update(Long id, UserDTO user) { userClient.update(id, user); }
    
    public void delete(Long id) { userClient.delete(id); }
}