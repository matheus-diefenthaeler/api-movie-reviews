package br.com.letscode.matheus.criticasdefilme.service;

import br.com.letscode.matheus.criticasdefilme.dto.RatingDto;
import br.com.letscode.matheus.criticasdefilme.entities.Rating;
import br.com.letscode.matheus.criticasdefilme.repositories.RatingRepository;
import br.com.letscode.matheus.criticasdefilme.repositories.UserRepository;
import br.com.letscode.matheus.criticasdefilme.request.RateRequest;
import br.com.letscode.matheus.criticasdefilme.response.MovieResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        return new RatingDto(entity);
    }

    public List<RatingDto> findByimdbID(String id) {
        return ratingRepository.findByImdbID(id);

    }

    public List<RatingDto> getRatings(String id) {
        return ratingRepository.findByImdbID(id);

    }
}
