package com.carrental.exception;

@SuppressWarnings("serial")
public class CarNotFoundException extends Exception {
    public CarNotFoundException(String message) {
        super(message);
    }
}
