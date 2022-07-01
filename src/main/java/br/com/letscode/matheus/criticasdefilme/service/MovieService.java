package br.com.letscode.matheus.criticasdefilme.service;

import br.com.letscode.matheus.criticasdefilme.response.MovieResponse;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieService {

    private static final String APIKEY = "23b2e8d6";
    private static final String API_OMDb_PATH = "http://www.omdbapi.com/";

    public Boolean existsMovieById(String id) {
        String url = new StringBuilder().append(API_OMDb_PATH).append("/?apikey=").
                append(APIKEY).append("&i=").append(id).toString();

        RestTemplate restTemplate = new RestTemplate();

        Object obj = restTemplate.getForObject(url, Object.class);

        Gson gson = new Gson();
        String s = gson.toJson(obj);
        MovieResponse movieResponse = gson.fromJson(s, MovieResponse.class);

        return movieResponse.getImdbID() != null;
    }
}
