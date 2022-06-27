package br.com.letscode.matheus.criticasdefilme.dto;

import br.com.letscode.matheus.criticasdefilme.entities.ReplyComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReplyCommentDto implements Serializable {

    private Long id;
    private Long userID;
    private String reply;

    public ReplyCommentDto(ReplyComment entity) {
        this.id = entity.getId();
        this.userID = entity.getUserID();
        this.reply = entity.getReply();
    }
}
