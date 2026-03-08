package com.yassine.insta_clone_backend.exception;

public class FollowAlreadyExistsException extends RuntimeException {
    public FollowAlreadyExistsException(String message) {
        super(message);
    }
}
