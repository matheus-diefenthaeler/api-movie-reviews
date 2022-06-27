package br.com.letscode.matheus.criticasdefilme.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyCommentRequest {

    private Long idRating;
    private String reply;
    private Long idUser;

}
