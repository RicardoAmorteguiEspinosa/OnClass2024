package com.pragma.arquetipobootcamp2024.configuration;

public class Constants {
    private Constants(){
        throw new IllegalStateException("utility class");
    }

    public static final String ELEMENT_NOT_FOUND_EXCEPTION_MESSAGE = "The element indicated does not exist";
    public static final String EMPTY_FIELD_EXCEPTION_MESSAGE = "Field %s can not be empty";
    public static final String NEGATIVE_NOT_ALLOWED_EXCEPTION_MESSAGE = "Field %s can not receive negative values";
    public static final String REPEATED_LIST_CONTENT_MESSAGE = "The list element with id %s is repeated";
    public static final String ELEMENT_ALREADY_EXISTS_EXCEPTION_MESSAGE = "The element with name %s that you want to create already exists";
}
