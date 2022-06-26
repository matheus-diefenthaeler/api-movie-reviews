package br.com.letscode.matheus.criticasdefilme.repositories;

import br.com.letscode.matheus.criticasdefilme.entities.Rating;
import br.com.letscode.matheus.criticasdefilme.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
}
