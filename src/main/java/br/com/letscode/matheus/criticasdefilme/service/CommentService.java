package br.com.letscode.matheus.criticasdefilme.service;

import br.com.letscode.matheus.criticasdefilme.dto.CommentDto;
import br.com.letscode.matheus.criticasdefilme.dto.ReplyCommentDto;
import br.com.letscode.matheus.criticasdefilme.entities.Comment;
import br.com.letscode.matheus.criticasdefilme.entities.CommentReply;
import br.com.letscode.matheus.criticasdefilme.entities.Rating;
import br.com.letscode.matheus.criticasdefilme.entities.User;
import br.com.letscode.matheus.criticasdefilme.repositories.CommentRepository;
import br.com.letscode.matheus.criticasdefilme.repositories.RatingRepository;
import br.com.letscode.matheus.criticasdefilme.repositories.ReplyCommentRepository;
import br.com.letscode.matheus.criticasdefilme.repositories.UserRepository;
import br.com.letscode.matheus.criticasdefilme.request.CommentRequest;
import br.com.letscode.matheus.criticasdefilme.request.DeleteRequest;
import br.com.letscode.matheus.criticasdefilme.request.ReplyCommentRequest;
import br.com.letscode.matheus.criticasdefilme.service.exceptions.PermissionDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private ReplyCommentRepository replyCommentRepository;

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

    @Transactional
    public void deleteReply(DeleteRequest deleteRequest) {
        Optional<CommentReply> reply = replyCommentRepository.findById(deleteRequest.getIdRate());
        Optional<User> user = userRepository.findById(deleteRequest.getIdUser());

        if (!userService.isAllowedToDelete(user.get())) {
            throw new PermissionDeniedException("User not allowed to delete replies!");
        }
        List<CommentReply> commentReplies;

        reply.get().setReply(null);

        replyCommentRepository.save(reply.get());
    }
}
