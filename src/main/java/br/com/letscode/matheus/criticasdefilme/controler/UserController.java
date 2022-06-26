package br.com.letscode.matheus.criticasdefilme.controler;

import br.com.letscode.matheus.criticasdefilme.dto.UserDto;
import br.com.letscode.matheus.criticasdefilme.entities.User;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import br.com.letscode.matheus.criticasdefilme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public String login(@RequestParam("user") String email, @RequestParam("password") String pwd) {

        String token = getJWTToken(email);
        User user = new User();
        user.setEmail(email);
        return token;

    }

    private String getJWTToken(String username) {
        String secretKey = "eyJhbGciOiJIUzUxMiJ9.ew0KICAic3ViIjogIjEyMzQ1Njc4OTAiLA0KICAibmFtZSI6ICJBbmlzaCBOYXRoIiwNCiAgImlhdCI6IDE1MTYyMzkwMjINCn0.CRBkfm1no-OCnHcNGTOBP4nf6CyvzuLyOwCeS0gpJlppZXpAugPbaZTtmyBegL0SfSJSk_ZyheBjib7Hol-VYw";
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
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto dto){
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



}
