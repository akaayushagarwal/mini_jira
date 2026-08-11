package com.example.mini_jira.exception;

public class InvalidStatusException extends RuntimeException{

    private final String fieldName;

    public InvalidStatusException(String fieldName, String message){
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName(){return fieldName;}

}
