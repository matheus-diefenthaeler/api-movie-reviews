package br.com.letscode.matheus.criticasdefilme.dto;

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
public class RatingDto implements Serializable {

    private Long id;
    private Long rate;
    private String imdbID;
    private String message;

    public RatingDto(Rating entity) {
        this.id = entity.getId();
        this.rate = entity.getRate();
        this.imdbID = entity.getImdbID();
        this.message = entity.getMessage();

    }
}
