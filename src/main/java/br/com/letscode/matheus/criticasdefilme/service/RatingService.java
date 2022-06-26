package br.com.letscode.matheus.criticasdefilme.service;

import br.com.letscode.matheus.criticasdefilme.dto.RatingDto;
import br.com.letscode.matheus.criticasdefilme.entities.Profile;
import br.com.letscode.matheus.criticasdefilme.entities.Rating;
import br.com.letscode.matheus.criticasdefilme.entities.User;
import br.com.letscode.matheus.criticasdefilme.repositories.RatingRepository;
import br.com.letscode.matheus.criticasdefilme.repositories.UserRepository;
import br.com.letscode.matheus.criticasdefilme.request.RateRequest;
import br.com.letscode.matheus.criticasdefilme.response.MovieResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public RatingDto saveRating(RateRequest rateRequest) {
        var entity = new Rating();
        entity.setRate(rateRequest.getRate());
        entity.setComment(rateRequest.getComment());
        entity.setUser(userRepository.findById(rateRequest.getIdUser()).get());
        entity.setImdbID(rateRequest.getImdbID());
        entity = ratingRepository.save(entity);

        increaseScore(rateRequest.getIdUser());
        upgradeProfile(rateRequest.getIdUser());

        return new RatingDto(entity);
    }

    public List<RatingDto> findByimdbID(String id) {
        return ratingRepository.findByImdbID(id);

    }

    public List<RatingDto> getRatings(String id) {
        return ratingRepository.findByImdbID(id);

    }

    public void increaseScore(Long idUser) {
        Optional<User> user = userRepository.findById(idUser);
        user.get().setScore(user.get().getScore() + 1);

    }

    public void upgradeProfile(Long idUser) {
        Optional<User> user = userRepository.findById(idUser);
        Long userScore = user.get().getScore();
        String userDescription = user.get().getProfile().getDescription();

        if (userScore >= 20 && userScore < 100) {
            user.get().setProfile(Profile.BASICO);
        } else if (userScore < 1000) {
            user.get().setProfile(Profile.AVANCADO);
        } else {
            user.get().setProfile(Profile.MODERADOR);
        }
    }
}
