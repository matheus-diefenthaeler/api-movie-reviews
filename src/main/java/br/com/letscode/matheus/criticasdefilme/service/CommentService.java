package br.com.letscode.matheus.criticasdefilme.service;

import br.com.letscode.matheus.criticasdefilme.dto.CommentDto;
import br.com.letscode.matheus.criticasdefilme.entities.Comment;
import br.com.letscode.matheus.criticasdefilme.entities.Rating;
import br.com.letscode.matheus.criticasdefilme.entities.User;
import br.com.letscode.matheus.criticasdefilme.repositories.CommentRepository;
import br.com.letscode.matheus.criticasdefilme.repositories.RatingRepository;
import br.com.letscode.matheus.criticasdefilme.repositories.UserRepository;
import br.com.letscode.matheus.criticasdefilme.request.CommentRequest;
import br.com.letscode.matheus.criticasdefilme.request.LikeRequest;
import br.com.letscode.matheus.criticasdefilme.service.exceptions.PermissionDeniedException;
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

    public void likeComment(LikeRequest likeRequest) {
        Optional<Comment> comment = commentRepository.findById(likeRequest.getIdComment());

        increaseLike(likeRequest, comment.get());
        commentRepository.save(comment.get());

    }

    @Transactional
    public void increaseLike(LikeRequest likeRequest, Comment comment) {
        if (likeRequest.getLike().equals(true)) {
            comment.setLikes(comment.getLikes() + 1);
        }
    }
}
