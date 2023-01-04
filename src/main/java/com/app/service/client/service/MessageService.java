package com.app.service.client.service;

public interface MessageService {

    String getMessage(String code);

    String getMessage(String code, Object... args);
}
