package br.com.letscode.matheus.criticasdefilme.controler;

import br.com.letscode.matheus.criticasdefilme.dto.UserDto;
import br.com.letscode.matheus.criticasdefilme.service.RatingService;
import br.com.letscode.matheus.criticasdefilme.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RatingService ratingService;


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
}
