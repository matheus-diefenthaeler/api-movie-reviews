package br.com.letscode.matheus.criticasdefilme.service;

import br.com.letscode.matheus.criticasdefilme.dto.UserDto;
import br.com.letscode.matheus.criticasdefilme.entities.Profile;
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

    public void verifyUser(String email, String pwd) {
        Optional<User> user = userRepository.findByEmailAndPassword(email,pwd);
        var entity = user.orElseThrow(()-> new UserNotFoundException("User not found!"));
    }


    public boolean isAllowedToComment(User user) {
        return (user != null && !user.getProfile().getDescription().equals(Profile.LEITOR.getDescription()));
    }

    public boolean isAllowedToDelete(User user) {
        return (user != null && user.getProfile().getDescription().equals(Profile.MODERADOR.getDescription()));
    }

    public void increaseUserScoreAndUpgrade(User user) {
        increaseScore(user);
        upgradeProfile(user);
    }

    public void increaseScore(User user) {
        user.setScore(user.getScore() + 1);
    }

    public void upgradeProfile(User user) {
        Long userScore = user.getScore();

        if (userScore >= 20 && userScore < 100) {
            user.setProfile(Profile.BASICO);
        } else if (userScore >= 100 && userScore < 1000) {
            user.setProfile(Profile.AVANCADO);
        } else if (userScore >= 1000) {
            user.setProfile(Profile.MODERADOR);
        }
    }
}
