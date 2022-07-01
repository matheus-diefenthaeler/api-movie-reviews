package br.com.letscode.matheus.criticasdefilme.service.exceptions;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException(String msg){
        super(msg);
    }
}
