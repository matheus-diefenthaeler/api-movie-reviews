package br.com.letscode.matheus.criticasdefilme.controler;

import br.com.letscode.matheus.criticasdefilme.dto.UserDto;
import br.com.letscode.matheus.criticasdefilme.request.RateRequest;
import br.com.letscode.matheus.criticasdefilme.request.ReplyCommentRequest;
import br.com.letscode.matheus.criticasdefilme.response.MovieResponse;
import br.com.letscode.matheus.criticasdefilme.service.RatingService;
import br.com.letscode.matheus.criticasdefilme.service.UserService;
import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RatingService ratingService;

    private static final String APIKEY = "23b2e8d6";
    private static final String API_OMDb_PATH = "http://www.omdbapi.com/";
    private static final String SECRET_KEY = "eyJhbGciOiJIUzUxMiJ9.ew0KICAic3ViIjogIjEyMzQ1Njc4OTAiLA0KICAibmFtZSI6ICJBbmlzaCBOYXRoIiwNCiAgImlhdCI6IDE1MTYyMzkwMjINCn0.CRBkfm1no-OCnHcNGTOBP4nf6CyvzuLyOwCeS0gpJlppZXpAugPbaZTtmyBegL0SfSJSk_ZyheBjib7Hol-VYw";

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public String login(@RequestParam("user") String email, @RequestParam("password") String pwd) {
        //TODO Lembrar de usar encrypt no password

        userService.verifyUser(email, pwd);
        return getJWTToken(email);
    }

    private String getJWTToken(String username) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        SECRET_KEY.getBytes()).compact();

        return "Bearer " + token;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto dto) {
        dto = userService.saveUser(dto);
        var uri =
                ServletUriComponentsBuilder.fromCurrentRequestUri()
                        .path("/registration")
                        .buildAndExpand(dto.getId())
                        .toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        var dto = userService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

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

    @RequestMapping(value = "/movie/rate", method = RequestMethod.POST)
    public ResponseEntity<Object> rateMovie(@RequestBody RateRequest rateRequest) {
        var dto = ratingService.saveRating(rateRequest);
        var uri =
                ServletUriComponentsBuilder.fromCurrentRequestUri()
                        .path("/movie/rate")
                        .buildAndExpand(dto.getId())
                        .toUri();
        return ResponseEntity.created(uri).body(rateRequest);
    }

    @RequestMapping(value = "/reply", method = RequestMethod.POST)
    public ResponseEntity<Object> repplyCommentRate(@RequestBody ReplyCommentRequest ReplyCommentRequest) {
        var dto = ratingService.replyRating(ReplyCommentRequest);
        var uri =
                ServletUriComponentsBuilder.fromCurrentRequestUri()
                        .path("/reply")
                        .buildAndExpand(dto.getId())
                        .toUri();
        return ResponseEntity.created(uri).body(ReplyCommentRequest);
    }

}
