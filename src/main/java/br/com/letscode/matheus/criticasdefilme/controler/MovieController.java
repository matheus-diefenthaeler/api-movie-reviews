package br.com.letscode.matheus.criticasdefilme.controler;

import br.com.letscode.matheus.criticasdefilme.response.MovieResponse;
import br.com.letscode.matheus.criticasdefilme.service.RatingService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MovieController {

    private static final String APIKEY = "23b2e8d6";
    private static final String API_OMDb_PATH = "http://www.omdbapi.com/";

    @Autowired
    private RatingService ratingService;


    @GetMapping(value = "/movie/{id}")
    public ResponseEntity<MovieResponse> findMovie(@PathVariable String id) {

        String url = new StringBuilder().append(API_OMDb_PATH).append("/?apikey=").
                append(APIKEY).append("&i=").append(id).toString();

        RestTemplate restTemplate = new RestTemplate();

        Object obj = restTemplate.getForObject(url, Object.class);

        Gson gson = new Gson();
        String s = gson.toJson(obj);
        MovieResponse movieResponse = gson.fromJson(s, MovieResponse.class);
        movieResponse.setMovieRating(ratingService.getRatings(id));

        return ResponseEntity.ok().body(movieResponse);
    }
}
