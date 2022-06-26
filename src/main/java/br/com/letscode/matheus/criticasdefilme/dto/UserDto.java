package br.com.letscode.matheus.criticasdefilme.dto;

import br.com.letscode.matheus.criticasdefilme.entities.Profile;
import br.com.letscode.matheus.criticasdefilme.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {

    private Long id;
    private String email;
    private String password;
    private Long score = 0L;
    private Profile profile = Profile.LEITOR;

    public UserDto(User entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.profile = entity.getProfile();
        this.score = entity.getScore();
    }
}
