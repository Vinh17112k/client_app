package com.app.service.client.service.impl;

import com.app.service.client.config.LocaleResolver;
import com.app.service.client.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

    @Override
    public String getMessage(String code) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()) .getRequest();
        return messageSource.getMessage(code, null, localeResolver.resolveLocale(request));
    }

    @Override
    public String getMessage(String code,  Object... args) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()) .getRequest();
        return messageSource.getMessage(code, args, localeResolver.resolveLocale(request));
    }
}
