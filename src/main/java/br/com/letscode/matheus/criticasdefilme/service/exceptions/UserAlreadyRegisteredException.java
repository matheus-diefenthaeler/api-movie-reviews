package br.com.letscode.matheus.criticasdefilme.service.exceptions;

public class UserAlreadyRegisteredException extends RuntimeException{
    public UserAlreadyRegisteredException(String msg){
        super(msg);
    }
}
