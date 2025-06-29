package com.michalz.weather_app_demo.exception;

public class ExternalApiException extends RuntimeException {
    public ExternalApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
