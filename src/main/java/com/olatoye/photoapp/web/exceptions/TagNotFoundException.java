package com.olatoye.photoapp.web.exceptions;

public class TagNotFoundException extends PhotoAppException{
    public TagNotFoundException(String message) {
        super(message);
    }
}
