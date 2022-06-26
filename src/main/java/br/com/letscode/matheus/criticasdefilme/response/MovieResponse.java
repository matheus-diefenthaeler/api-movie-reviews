package br.com.letscode.matheus.criticasdefilme.response;

import br.com.letscode.matheus.criticasdefilme.dto.RatingDto;
import br.com.letscode.matheus.criticasdefilme.entities.Rating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponse implements Serializable {

    private String Title;
    private String Year;
    private String Runtime;
    private String Genre;
    private String Director;
    private String Actors;
    private String Country;
    private String imdbID;
    private List<RatingDto> MovieRatings = new ArrayList<>();

    public void setMovieRating(List<RatingDto> dto){
        MovieRatings = dto;
    }

}
