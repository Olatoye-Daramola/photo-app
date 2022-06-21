package com.olatoye.photoapp.web.exceptions;

public class PhotoNotFoundException extends PhotoAppException{
    public PhotoNotFoundException(String message) {
        super(message);
    }
}
