package com.app.service.client.service;

import com.app.service.client.config.exceptions.ValidateException;
import com.app.service.client.domain.customer.*;
import com.app.service.client.model.Customer;
import com.app.service.client.utils.PaginationResultUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;
import java.util.List;

public interface CustomerService extends UserDetailsService {

    CustomerDTO createCustomer(CustomerCreatorDTO customerCreatorDTO)
        throws ValidateException;

    CustomerTokenDTO updateCustomer(CustomerCreatorDTO customerCreatorDTO)
        throws ValidateException, IOException;

    Customer loadByUsername(String username) throws UsernameNotFoundException;

    Boolean changePassword(PasswordDTO passwordDTO) throws ValidateException;

    CustomerDTO forgotPassword(PasswordDTO passwordDTO) throws ValidateException;

    String supportForEmail(String email) throws ValidateException;
}
