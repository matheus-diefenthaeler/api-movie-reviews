package br.com.letscode.matheus.criticasdefilme.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuplicateRequest {

    private Long idRating;
    private Long idComment;
    private Long idUser;
    private Boolean duplicated;

}
