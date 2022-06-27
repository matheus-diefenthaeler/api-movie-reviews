package br.com.letscode.matheus.criticasdefilme.controler;

import br.com.letscode.matheus.criticasdefilme.request.ReplyCommentRequest;
import br.com.letscode.matheus.criticasdefilme.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class ReplyCommentController {

    @Autowired
    private RatingService ratingService;


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
