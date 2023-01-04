package com.app.service.client.controller;

import com.app.service.client.model.Customer;
import com.app.service.client.repository.CustomerRepository;
import com.app.service.client.config.oauth2.UserPrincipal;
import com.app.service.client.config.security.CurrentUser;
import com.app.service.client.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetController {

    @Autowired
    private CustomerRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public Customer getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getCustomerId())
            .orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userPrincipal.getCustomerId()));
    }
}
