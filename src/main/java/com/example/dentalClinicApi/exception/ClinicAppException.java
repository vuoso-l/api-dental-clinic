package com.example.dentalClinicApi.exception;

import org.springframework.http.HttpStatus;

public class ClinicAppException extends RuntimeException{

    private HttpStatus state;
    private String message;

    public ClinicAppException(HttpStatus state, String message) {
        super();
        this.state = state;
        this.message = message;
    }

    public ClinicAppException(HttpStatus state, String message, String message1) {
        super();
        this.state = state;
        this.message = message;
        this.message = message1;
    }

    public HttpStatus getState() {
        return state;
    }

    public void setState(HttpStatus state) {
        this.state = state;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
