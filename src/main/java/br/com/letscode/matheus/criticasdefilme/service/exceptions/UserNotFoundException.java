package br.com.letscode.matheus.criticasdefilme.service.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String msg){
        super(msg);
    }
}
