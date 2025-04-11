package com.carrental.exception;

@SuppressWarnings("serial")
public class LeaseNotFoundException extends Exception {
    public LeaseNotFoundException(String message) {
        super(message);
    }
}
