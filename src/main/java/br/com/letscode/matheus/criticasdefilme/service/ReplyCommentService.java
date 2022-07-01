package br.com.letscode.matheus.criticasdefilme.service;

import br.com.letscode.matheus.criticasdefilme.dto.ReplyCommentDto;
import br.com.letscode.matheus.criticasdefilme.entities.Comment;
import br.com.letscode.matheus.criticasdefilme.entities.Rating;
import br.com.letscode.matheus.criticasdefilme.entities.CommentReply;
import br.com.letscode.matheus.criticasdefilme.entities.User;
import br.com.letscode.matheus.criticasdefilme.repositories.CommentRepository;
import br.com.letscode.matheus.criticasdefilme.repositories.RatingRepository;
import br.com.letscode.matheus.criticasdefilme.repositories.ReplyCommentRepository;
import br.com.letscode.matheus.criticasdefilme.repositories.UserRepository;
import br.com.letscode.matheus.criticasdefilme.request.DeleteRequest;
import br.com.letscode.matheus.criticasdefilme.request.ReplyCommentRequest;
import br.com.letscode.matheus.criticasdefilme.service.exceptions.CommentNotFoundException;
import br.com.letscode.matheus.criticasdefilme.service.exceptions.PermissionDeniedException;
import br.com.letscode.matheus.criticasdefilme.service.exceptions.RatingNotFoundException;
import br.com.letscode.matheus.criticasdefilme.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReplyCommentService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ReplyCommentRepository replyCommentRepository;

    @Autowired
    private CommentRepository CommentRepository;

    @Transactional
    public ReplyCommentDto replyComment(ReplyCommentRequest replyRequest) {
        Optional<Rating> rating = ratingRepository.findById(replyRequest.getIdRating());
        Optional<User> user = userRepository.findById(replyRequest.getIdUser());
        Optional<Comment> comment = CommentRepository.findById(replyRequest.getIdComment());
        rating.orElseThrow(()-> new RatingNotFoundException("Rating not found!"));
        user.orElseThrow(()-> new UserNotFoundException("User not found!"));
        comment.orElseThrow(()-> new CommentNotFoundException("Comment not found!"));

        if (!userService.isAllowedToComment(user.get())) {
            throw new PermissionDeniedException("User not allowed to reply");
        }
        var entity = new CommentReply();

        entity.setReply(replyRequest.getReply());
        entity.setUserID(replyRequest.getIdUser());
        entity.setComment(comment.get());
        entity.setRating(rating.get());
        entity = replyCommentRepository.save(entity);

        userService.increaseUserScoreAndUpgrade(user.get());
        return new ReplyCommentDto(entity);
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
