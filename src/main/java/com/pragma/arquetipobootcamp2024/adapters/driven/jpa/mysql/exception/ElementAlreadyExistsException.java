package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.exception;

public class ElementAlreadyExistsException extends RuntimeException{
    public ElementAlreadyExistsException(String message){ super(message); }
}
