package br.com.letscode.matheus.criticasdefilme.service;

import br.com.letscode.matheus.criticasdefilme.controler.exceptions.MovieNotFoundException;
import br.com.letscode.matheus.criticasdefilme.dto.RatingDto;
import br.com.letscode.matheus.criticasdefilme.entities.Rating;
import br.com.letscode.matheus.criticasdefilme.entities.User;
import br.com.letscode.matheus.criticasdefilme.repositories.RatingRepository;
import br.com.letscode.matheus.criticasdefilme.repositories.ReplyCommentRepository;
import br.com.letscode.matheus.criticasdefilme.repositories.UserRepository;
import br.com.letscode.matheus.criticasdefilme.request.DeleteRequest;
import br.com.letscode.matheus.criticasdefilme.request.RateRequest;
import br.com.letscode.matheus.criticasdefilme.service.exceptions.PermissionDeniedException;
import br.com.letscode.matheus.criticasdefilme.service.exceptions.RatingNotFoundException;
import br.com.letscode.matheus.criticasdefilme.service.exceptions.UserNotFoundException;
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

    @Autowired
    private ReplyCommentRepository replyCommentRepository;

    @Autowired
    private ReplyCommentService replyCommentService;

    @Autowired
    private MovieService movieService;

    @Transactional
    public RatingDto saveRating(RateRequest rateRequest) {
        Optional<User> user = userRepository.findById(rateRequest.getIdUser());
        user.orElseThrow(()-> new UserNotFoundException("User not found!"));

        if(!movieService.existsMovieById(rateRequest.getImdbID())){
            throw new MovieNotFoundException("Movie not found for the given imdbID!");
        }

        var entity = new Rating();

        entity.setMessage(rateRequest.getMessage());
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

    @Transactional
    public void deleteRating(DeleteRequest deleteRequest) {
        Optional<Rating> rating = ratingRepository.findById(deleteRequest.getIdRate());
        Optional<User> user = userRepository.findById(deleteRequest.getIdUser());
        user.orElseThrow(()-> new UserNotFoundException("User not found!"));
        rating.orElseThrow(()-> new RatingNotFoundException("Rating not found!"));

        if (!userService.isAllowedToDelete(user.get())) {
            throw new PermissionDeniedException("User not allowed to delete ratings!");
        }

        ratingRepository.deleteById(deleteRequest.getIdRate());
    }
}
