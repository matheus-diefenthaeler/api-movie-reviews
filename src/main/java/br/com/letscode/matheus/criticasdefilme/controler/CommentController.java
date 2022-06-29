package br.com.letscode.matheus.criticasdefilme.controler;

import br.com.letscode.matheus.criticasdefilme.request.CommentRequest;
import br.com.letscode.matheus.criticasdefilme.request.ReplyCommentRequest;
import br.com.letscode.matheus.criticasdefilme.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public ResponseEntity<Object> repplyCommentRate(@RequestBody CommentRequest commentRequest) {
        var dto = commentService.comment(commentRequest);
        var uri =
                ServletUriComponentsBuilder.fromCurrentRequestUri()
                        .path("/comment")
                        .buildAndExpand(dto.getId())
                        .toUri();
        return ResponseEntity.created(uri).body(commentRequest);
    }
}
