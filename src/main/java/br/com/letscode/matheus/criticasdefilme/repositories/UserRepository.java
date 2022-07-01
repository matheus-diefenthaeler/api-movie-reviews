package br.com.letscode.matheus.criticasdefilme.repositories;

import br.com.letscode.matheus.criticasdefilme.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndPassword(String email, String pwd);

    Optional<User> findByEmail(String email);
}
