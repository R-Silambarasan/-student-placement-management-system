package com.placement.exception;

public class JobDriveNotFoundException
        extends RuntimeException {

    public JobDriveNotFoundException(String message) {
        super(message);
    }
}