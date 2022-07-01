package br.com.letscode.matheus.criticasdefilme.service;

import br.com.letscode.matheus.criticasdefilme.dto.CommentDto;
import br.com.letscode.matheus.criticasdefilme.entities.Comment;
import br.com.letscode.matheus.criticasdefilme.entities.Rating;
import br.com.letscode.matheus.criticasdefilme.entities.User;
import br.com.letscode.matheus.criticasdefilme.repositories.CommentRepository;
import br.com.letscode.matheus.criticasdefilme.repositories.RatingRepository;
import br.com.letscode.matheus.criticasdefilme.repositories.UserRepository;
import br.com.letscode.matheus.criticasdefilme.request.CommentRequest;
import br.com.letscode.matheus.criticasdefilme.request.DisLikeRequest;
import br.com.letscode.matheus.criticasdefilme.request.DuplicateRequest;
import br.com.letscode.matheus.criticasdefilme.request.LikeRequest;
import br.com.letscode.matheus.criticasdefilme.service.exceptions.PermissionDeniedException;
import br.com.letscode.matheus.criticasdefilme.service.exceptions.RatingNotFoundException;
import br.com.letscode.matheus.criticasdefilme.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public CommentDto comment(CommentRequest commentRequest) {
        Optional<User> user = userRepository.findById(commentRequest.getIdUser());
        Optional<Rating> rating = ratingRepository.findById(commentRequest.getIdRating());
        user.orElseThrow(()-> new UserNotFoundException("User not found!"));
        rating.orElseThrow(()-> new RatingNotFoundException("Rating not found!"));

        if (!userService.isAllowedToComment(user.get())) {
            throw new PermissionDeniedException("User not allowed to Comment");
        }
        var entity = new Comment();

        entity.setMessage(commentRequest.getMessage());
        entity.setUserID(commentRequest.getIdUser());
        entity.setRating(rating.get());
        entity = commentRepository.save(entity);

        userService.increaseUserScoreAndUpgrade(user.get());
        return new CommentDto(entity);
    }

    @Transactional
    public void likeComment(LikeRequest likeRequest) {
        Optional<Comment> comment = commentRepository.findById(likeRequest.getIdComment());

        increaseLike(likeRequest, comment.get());
        commentRepository.save(comment.get());
    }

    @Transactional
    public void disLikeComment(DisLikeRequest disLikeRequest) {
        Optional<Comment> comment = commentRepository.findById(disLikeRequest.getIdComment());

        increaseDisLike(disLikeRequest, comment.get());
        commentRepository.save(comment.get());
    }

    @Transactional
    public void setCommentDuplicated(DuplicateRequest duplicateRequest) {
        Optional<Comment> comment = commentRepository.findById(duplicateRequest.getIdComment());

        comment.get().setRepeated(duplicateRequest.getDuplicated());

        commentRepository.save(comment.get());
    }


    public void increaseLike(LikeRequest likeRequest, Comment comment) {
        if (likeRequest.getLike().equals(true)) {
            comment.setLikes(comment.getLikes() + 1);
        }
    }

    public void increaseDisLike(DisLikeRequest disLikeRequest, Comment comment) {
        if (disLikeRequest.getDisLike().equals(true)) {
            comment.setDisLikes(comment.getDisLikes() + 1);
        }
    }
}
