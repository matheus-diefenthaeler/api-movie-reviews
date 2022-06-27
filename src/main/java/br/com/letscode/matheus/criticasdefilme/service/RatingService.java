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
    private ReplyCommentRepository replyCommentRepository;

    @Transactional
    public RatingDto saveRating(RateRequest rateRequest) {
        Optional<User> user = userRepository.findById(rateRequest.getIdUser());
        var entity = new Rating();

        entity.setRate(rateRequest.getRate());

        if (rateRequest.getComment() != null) {
            checkPermission(rateRequest.getIdUser());
            entity.setComment(rateRequest.getComment());
        }

        entity.setUser(user.get());
        entity.setImdbID(rateRequest.getImdbID());
        entity = ratingRepository.save(entity);

        increaseUserScoreAndUpgrade(user.get());

        return new RatingDto(entity);
    }

    public ReplyCommentDto replyRating(ReplyCommentRequest replyRequest) {
        Optional<User> user = userRepository.findById(replyRequest.getIdUser());
        Optional<Rating> rating = ratingRepository.findById(replyRequest.getIdRating());

        if (!isAllowedToComment(replyRequest.getIdUser())) {
            throw new PermissionDeniedException("User not allowed to reply");
        }
        var entity = new ReplyComment();

        entity.setReply(replyRequest.getReply());
        entity.setUserID(replyRequest.getIdUser());
        entity.setRating(rating.get());
        entity = replyCommentRepository.save(entity);

        increaseUserScoreAndUpgrade(user.get());
        return new ReplyCommentDto(entity);
    }

    public List<RatingDto> findByimdbID(String id) {
        return ratingRepository.findByImdbID(id);
    }

    public List<RatingDto> getRatings(String id) {
        return ratingRepository.findByImdbID(id);
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

    public boolean checkPermission(Long idUser) {
        Optional<User> user = userRepository.findById(idUser);
        if (user.get().getProfile().getDescription().equals("Leitor")) {
            throw new PermissionDeniedException("User not allowed to comment");
        }
        return true;
    }

    public boolean isAllowedToComment(Long idUser) {
        if (idUser != null) return false;
        Optional<User> user = userRepository.findById(idUser);
        return (!user.get().getProfile().getDescription().equals("Leitor"));
    }
}
