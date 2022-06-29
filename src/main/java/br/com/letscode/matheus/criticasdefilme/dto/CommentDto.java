package br.com.letscode.matheus.criticasdefilme.dto;

import br.com.letscode.matheus.criticasdefilme.entities.Comment;
import br.com.letscode.matheus.criticasdefilme.entities.CommentReply;
import br.com.letscode.matheus.criticasdefilme.entities.Rating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto implements Serializable {

    private Long id;
    private Long userID;
    private String message;
    private Rating rating;

    public CommentDto(Comment entity) {
        this.id = entity.getId();
        this.userID = entity.getUserID();
        this.message = entity.getMessage();
        this.rating = entity.getRating();
    }
}
