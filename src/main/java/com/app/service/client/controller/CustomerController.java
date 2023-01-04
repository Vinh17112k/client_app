package com.app.service.client.controller;

import com.app.service.client.contants.Constants;
import com.app.service.client.domain.auth.ApiResponse;
import com.app.service.client.domain.auth.AuthRequest;
import com.app.service.client.domain.auth.SignUpRequest;
import com.app.service.client.domain.customer.CustomerCreatorDTO;
import com.app.service.client.domain.customer.CustomerDTO;
import com.app.service.client.domain.customer.CustomerTokenDTO;
import com.app.service.client.domain.customer.PasswordDTO;
import com.app.service.client.model.AuthProvider;
import com.app.service.client.model.Customer;
import com.app.service.client.repository.CustomerRepository;
import com.app.service.client.utils.DataUtils;
import com.app.service.client.config.exceptions.GlobalExceptionHandler.ApiResponseData;
import com.app.service.client.config.exceptions.ValidateException;
import com.app.service.client.config.oauth2.UserPrincipal;
import com.app.service.client.config.security.SecurityUtilsClient;
import com.app.service.client.config.security.service.TokenProvider;
import com.app.service.client.exception.BadRequestException;
import com.app.service.client.mapper.CusMapper;
import com.app.service.client.service.CustomerService;
import com.app.service.client.service.MessageService;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Log4j2
@RestController
@RequestMapping("/api/v1/customer")
@Builder
public class CustomerController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MessageService messageService;
    @Autowired
    private CustomerRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("")
    public ResponseEntity<CustomerTokenDTO> login(@RequestBody @Valid AuthRequest request)
        throws Exception {
        try {
            Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                    request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            UserPrincipal userPrincipal = (UserPrincipal) authenticate.getPrincipal();
            Customer user = customerRepository.findById(userPrincipal.getCustomerId()).get();

            if (Constants.STATUS.INACTIVE.getCode().equals(user.getStatus())) {
                throw new InternalAuthenticationServiceException("User locked!");
            }

            HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String ip = DataUtils.getclientIp(httpServletRequest);

            log.info("{} Login with ip {}", user.getUsername(), ip);
            log.info("{} Remote with ip {}", user.getUsername(),
                httpServletRequest.getRemoteAddr());
            CustomerDTO userDTO = CusMapper.INSTANCE.toDto(user);

            CustomerTokenDTO userTokenDTO = CustomerTokenDTO.builder()
                .customerDTO(userDTO)
                .token(tokenProvider.createToken(authenticate))
                .build();
            return ResponseEntity.ok(userTokenDTO);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ValidateException(messageService.getMessage("login.invadlid"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseData<CustomerDTO>> registerUser(
        @RequestBody CustomerCreatorDTO customerCreatorDTO) throws ValidateException {
        return ResponseEntity.ok(ApiResponseData.<CustomerDTO>builder()
            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
            .messages(Arrays.asList("success"))
            .data(customerService.createCustomer(customerCreatorDTO))
            .build());
    }

    @PostMapping(value = "/update", consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponseData<CustomerDTO>> updateUser(
        @ParameterObject CustomerCreatorDTO customerCreatorDTO,
        @RequestParam(required = false) MultipartFile avatar)
        throws ValidateException, IOException {
        return ResponseEntity.ok(ApiResponseData.<CustomerDTO>builder()
            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
            .messages(Arrays.asList("success"))
            .data(customerService.updateCustomer(customerCreatorDTO))
            .build());
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponseData<Boolean>> changePassword(
        @RequestBody PasswordDTO passwordDTO) throws ValidateException {
        return ResponseEntity.ok(ApiResponseData.<Boolean>builder()
            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
            .messages(Arrays.asList("success"))
            .data(customerService.changePassword(passwordDTO))
            .build());
    }

    @PostMapping("forgot-password")
    public ResponseEntity<ApiResponseData<CustomerDTO>> forgotPassword(
        @RequestBody PasswordDTO passwordDTO) throws ValidateException {
        return ResponseEntity.ok(ApiResponseData.<CustomerDTO>builder()
            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
            .messages(Arrays.asList(messageService.getMessage("error.code.success")))
            .data(customerService.forgotPassword(passwordDTO))
            .build());
    }

    @GetMapping("/send-verify-code")
    public ResponseEntity<ApiResponseData<String>> sendCode(@RequestParam("email") String email)
        throws ValidateException {
        return ResponseEntity.ok(ApiResponseData.<String>builder()
            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
            .messages(Arrays.asList("success"))
            .data(customerService.supportForEmail(email))
            .build());
    }

    //test social
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        // Creating user's account
        Customer user = new Customer();
        user.setUsername(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Customer result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
            .fromCurrentContextPath().path("/user/me")
            .buildAndExpand(result.getCustomerId()).toUri();

        return ResponseEntity.created(location)
            .body(new ApiResponse(true, "User registered successfully@"));
    }

    @GetMapping("/detail")
    public ResponseEntity<ApiResponseData<Customer>> customerDetail() {
        Long customerId = SecurityUtilsClient.getCustomerId();
        return ResponseEntity.ok(ApiResponseData.<Customer>builder()
            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
            .messages(Arrays.asList("success"))
            .data(customerRepository.getCustomer(customerId))
            .build());
    }
}
