package com.example.mini_jira.exception;

public class UserAlreadyPresentException extends RuntimeException{

    public UserAlreadyPresentException(){
        String message = "User Already Present";
        super(message);
    }

}
