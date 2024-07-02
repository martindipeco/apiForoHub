package com.alura.foro.infra.exception;

public class ValidacionIntegridadException extends RuntimeException{

    public ValidacionIntegridadException (String s)
    {
        super(s);
    }
}
