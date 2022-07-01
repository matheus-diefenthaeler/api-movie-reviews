package br.com.letscode.matheus.criticasdefilme.service.exceptions;

public class RatingNotFoundException extends RuntimeException{
    public RatingNotFoundException(String msg){
        super(msg);
    }
}
