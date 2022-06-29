package br.com.letscode.matheus.criticasdefilme.controler;

import br.com.letscode.matheus.criticasdefilme.request.DeleteRequest;
import br.com.letscode.matheus.criticasdefilme.request.RateRequest;
import br.com.letscode.matheus.criticasdefilme.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class RatingController {

    @Autowired
    private RatingService ratingService;

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

    @RequestMapping(value = "/delete-comment", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteRating(@RequestBody DeleteRequest deleteRequest) {
        ratingService.deleteById(deleteRequest.getIdRate());
        return ResponseEntity.noContent().build();
    }

//    @RequestMapping(value = "/cite/{id}", method = RequestMethod.POST)
//    public ResponseEntity<Object> citeComment(@PathVariable Long idComment) {
//        var dto = ratingService.citeComment(idComment);
//        var uri =
//                ServletUriComponentsBuilder.fromCurrentRequestUri()
//                        .path("/cite/{id}")
//                        .buildAndExpand(dto.getId())
//                        .toUri();
//        return ResponseEntity.created(uri).body(rateRequest);
//    }

}
