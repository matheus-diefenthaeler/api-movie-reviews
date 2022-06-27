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
        var entity = new Rating();

        entity.setRate(rateRequest.getRate());

        if (rateRequest.getComment() != null) {
            checkPermission(rateRequest.getIdUser());
            entity.setComment(rateRequest.getComment());
        }

        entity.setUser(userRepository.findById(rateRequest.getIdUser()).get());
        entity.setImdbID(rateRequest.getImdbID());
        entity = ratingRepository.save(entity);

        increaseScore(rateRequest.getIdUser());
        upgradeProfile(rateRequest.getIdUser());

        return new RatingDto(entity);
    }

    public ReplyCommentDto replyRating(ReplyCommentRequest replyRequest) {
        if (!isAllowedToComment(replyRequest.getIdUser())) {
            throw new PermissionDeniedException("User not allowed to comment");
        }
        var entity = new ReplyComment();

        entity.setReply(replyRequest.getReply());
        entity.setUserID(replyRequest.getIdUser());
        entity.setRating(ratingRepository.findById(replyRequest.getIdRating()).get());
        entity = replyCommentRepository.save(entity);

        increaseScore(replyRequest.getIdUser());
        upgradeProfile(replyRequest.getIdUser());

        return new ReplyCommentDto(entity);
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
        } else if (userScore >= 100 && userScore < 1000) {
            user.get().setProfile(Profile.AVANCADO);
        } else if (userScore >= 1000) {
            user.get().setProfile(Profile.MODERADOR);
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
