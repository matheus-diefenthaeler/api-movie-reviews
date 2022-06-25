package br.com.letscode.matheus.criticasdefilme.controler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

    @RequestMapping(value = "/test")
    public String test(){
        return "Tested!";
    }

}
