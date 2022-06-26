package br.com.letscode.matheus.criticasdefilme.service;

import br.com.letscode.matheus.criticasdefilme.dto.UserDto;
import br.com.letscode.matheus.criticasdefilme.entities.User;
import br.com.letscode.matheus.criticasdefilme.repositories.UserRepository;
import br.com.letscode.matheus.criticasdefilme.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserDto saveUser(UserDto dto) {
        var entity = new User();
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setProfile(dto.getProfile());
        entity.setScore(dto.getScore());
        entity = userRepository.save(entity);
        return new UserDto(entity);
    }

    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        Optional<User> user = userRepository.findById(id);
       var entity = user.orElseThrow(()-> new UserNotFoundException("User not found!"));
       return new UserDto(entity);
    }
}
