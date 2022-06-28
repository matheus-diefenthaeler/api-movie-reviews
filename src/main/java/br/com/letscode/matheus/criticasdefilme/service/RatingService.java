package br.com.letscode.matheus.criticasdefilme.service;

import br.com.letscode.matheus.criticasdefilme.dto.RatingDto;
import br.com.letscode.matheus.criticasdefilme.dto.ReplyCommentDto;
import br.com.letscode.matheus.criticasdefilme.entities.ReplyComment;
import br.com.letscode.matheus.criticasdefilme.entities.Profile;
import br.com.letscode.matheus.criticasdefilme.entities.Rating;
import br.com.letscode.matheus.criticasdefilme.entities.User;
import br.com.letscode.matheus.criticasdefilme.repositories.RatingRepository;
import br.com.letscode.matheus.criticasdefilme.repositories.ReplyCommentRepository;
import br.com.letscode.matheus.criticasdefilme.repositories.UserRepository;
import br.com.letscode.matheus.criticasdefilme.request.RateRequest;
import br.com.letscode.matheus.criticasdefilme.request.ReplyCommentRequest;
import br.com.letscode.matheus.criticasdefilme.service.exceptions.PermissionDeniedException;
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

    @Autowired
    private UserService userService;

    @Transactional
    public RatingDto saveRating(RateRequest rateRequest) {
        Optional<User> user = userRepository.findById(rateRequest.getIdUser());

        if (rateRequest.getComment() != null && !userService.isAllowedToComment(user.get())) {
            throw new PermissionDeniedException("User not allowed to comment");
        }

        var entity = new Rating();

        entity.setComment(rateRequest.getComment());
        entity.setRate(rateRequest.getRate());
        entity.setUser(user.get());
        entity.setImdbID(rateRequest.getImdbID());
        entity = ratingRepository.save(entity);

        userService.increaseUserScoreAndUpgrade(user.get());

        return new RatingDto(entity);
    }

    public List<RatingDto> findByimdbID(String id) {
        return ratingRepository.findByImdbID(id);
    }

    public List<RatingDto> getRatings(String id) {
        return ratingRepository.findByImdbID(id);
    }
}
