package com.example.project.exception;

public class TimeExceededException extends RuntimeException{
    public TimeExceededException() {
        super("Time end");
    }
}
