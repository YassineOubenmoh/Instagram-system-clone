package com.yassine.insta_clone_backend.exception;

public class NoResourceExistsException extends RuntimeException {
    public NoResourceExistsException(String message) {
        super(message);
    }
}
