package com.example.mini_jira.exception;

public class ResourceNotFoundException extends RuntimeException{

    private final String fieldName;
    
    public ResourceNotFoundException(String fieldName, String message){
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName(){return fieldName;}

}
