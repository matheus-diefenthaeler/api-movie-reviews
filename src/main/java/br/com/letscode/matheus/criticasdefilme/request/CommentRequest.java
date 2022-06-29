package br.com.letscode.matheus.criticasdefilme.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {

    private Long idRating;
    private String message;
    private Long idUser;

}
