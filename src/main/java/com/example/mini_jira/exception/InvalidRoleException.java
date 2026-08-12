package com.example.mini_jira.exception;

public class InvalidRoleException extends RuntimeException{
    
    public InvalidRoleException(){
        String message = "Invalid Role";
        super(message);
    }
}
