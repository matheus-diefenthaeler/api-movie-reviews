package br.com.letscode.matheus.criticasdefilme.service.exceptions;

public class PermissionDeniedException extends RuntimeException{
    public PermissionDeniedException(String msg){
        super(msg);
    }
}
