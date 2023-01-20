package com.yudiprojects.firstplugin.exceptions;

public class NotFoundException extends IllegalArgumentException {
    public NotFoundException(String message) {
        super(message);
    }
}
