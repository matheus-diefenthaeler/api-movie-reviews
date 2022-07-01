package br.com.letscode.matheus.criticasdefilme.controler.exceptions;

public class MovieNotFoundException extends RuntimeException{
    public MovieNotFoundException(String msg){
        super(msg);
    }
}
