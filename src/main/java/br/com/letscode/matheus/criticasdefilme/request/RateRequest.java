package br.com.letscode.matheus.criticasdefilme.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RateRequest {

    private Long idUser;
    private String comment;
    private Long rate;
    private String imdbID;
}
