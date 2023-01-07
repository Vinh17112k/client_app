package com.app.service.client.service.impl;

import com.app.service.client.config.CloudinaryConfig;
import com.app.service.client.config.exceptions.ValidateException;
import com.app.service.client.config.security.SecurityUtilsClient;
import com.app.service.client.config.security.service.TokenProvider;
import com.app.service.client.domain.customer.*;
import com.app.service.client.mapper.CusMapper;
import com.app.service.client.model.Customer;
import com.app.service.client.repository.CustomerRepository;
import com.app.service.client.service.CustomerService;
import com.app.service.client.service.MessageService;
import com.app.service.client.utils.DataUtils;
import com.app.service.client.utils.PaginationResultUtils;
import com.app.service.client.validator.CommonValidator;
import com.cloudinary.utils.ObjectUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.String.format;

@Log4j2
@Service
@Data
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CusMapper cusMapper;
    @Autowired
    private CommonValidator commonValidator;
    @Autowired
    private MessageService messageService;
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private Configuration config;
    @Autowired
    private CloudinaryConfig cloudinaryConfig;
    @Autowired
    private TokenProvider tokenProvider;

    @Override
    public CustomerDTO createCustomer(CustomerCreatorDTO customerCreatorDTO)
        throws ValidateException {
        commonValidator.validateInput(customerCreatorDTO);
        validateInfo(customerCreatorDTO, Boolean.TRUE, null);
        Customer customer = cusMapper.toEntity(customerCreatorDTO);
        customer.setStatus(1);
        customer.setPassword(passwordEncoder.encode(customerCreatorDTO.getPassword()));
        customer.setPasswordRaw(customerCreatorDTO.getPassword());
        customer.setPhone(DataUtils.changePhoneToPlus84(customerCreatorDTO.getPhone()));
        customer.setCountryId(1L);
        customerRepository.insertCustomerNative("new customer", customer.getIsDeleted(),
            customer.getBirthday(),
            customer.getCountryId(), customer.getEmail(), customer.getFullName(),
            customer.getPassword(), customer.getPasswordRaw(), customer.getPhone(),
            customer.getStatus(), customer.getUsername()
        );

        return cusMapper.toDto(customer);
    }

    @Override
    public CustomerTokenDTO updateCustomer(CustomerCreatorDTO customerCreatorDTO)
        throws ValidateException, IOException {
        commonValidator.validateInput(customerCreatorDTO);
        Customer customer = customerRepository.findById(customerCreatorDTO.getCustomerId())
            .orElseThrow(() -> new ValidateException(
                messageService.getMessage("error.user.notfound")
            ));
        List<Long> ids = new ArrayList<>();
        ids.add(customerCreatorDTO.getCustomerId());
        validateInfo(customerCreatorDTO, Boolean.FALSE, ids);
        customerCreatorDTO.setStatus(customer.getStatus());
        if (!DataUtils.isNull(customerCreatorDTO.getStatus())) {
            customerCreatorDTO.setStatus(customer.getStatus());
        }
        Customer cusUpdate = cusMapper.toEntity(customerCreatorDTO);
      if (DataUtils.isNull(customerCreatorDTO.getAvatar()) && !DataUtils.isNullOrEmpty(
          customer.getAvatarUser())) {
        cusUpdate.setAvatarUser(customer.getAvatarUser());
      } else if (!DataUtils.isNull(customerCreatorDTO.getAvatar())) {
        MultipartFile avatar = customerCreatorDTO.getAvatar();
        Map uploadResult = cloudinaryConfig.upload(avatar.getBytes(),
                  ObjectUtils.asMap("Home/product_images", "auto"));
          String filePath = uploadResult.get("url").toString();
        cusUpdate.setAvatarUser(filePath);
      }
        String password = customer.getPassword();
        String passwordRaw = customer.getPasswordRaw();
        cusUpdate.setPassword(password);
        cusUpdate.setCountryId(1L);
        cusUpdate.setPasswordRaw(passwordRaw);
        customerRepository.save(cusUpdate);
        CustomerDTO customerDTO = cusMapper.toDto(cusUpdate);
        if (!DataUtils.isNullOrEmpty(cusUpdate.getAvatarUser())) {
            customerDTO.setAvatar(cusUpdate.getAvatarUser());
        }
        CustomerTokenDTO userTokenDTO = CustomerTokenDTO.builder()
                .customerDTO(customerDTO)
                .token(null)
                .build();
        return userTokenDTO;
    }

    @Override
    public Customer loadByUsername(String username) throws UsernameNotFoundException {
        return customerRepository
            .findByUsername(username)
            .orElseThrow(
                () -> new UsernameNotFoundException(
                    format("User: %s, not found", username)
                )
            );
    }

    private void validateInfo(
        CustomerCreatorDTO input, Boolean isCreate, List<Long> userId) throws ValidateException {
      if (!DataUtils.isPhone(input.getPhone())) {
        throw new ValidateException(messageService.getMessage("error.code.invalid.phone"));
      }
      if (!DataUtils.isEmail(input.getEmail())) {
        throw new ValidateException(messageService.getMessage("error.code.invalid.email"));
      }
        if (Boolean.TRUE.equals(isCreate)) {
          if (customerRepository.existsByUsername(input.getUsername())) {
            throw new ValidateException(
                messageService.getMessage("error.code.exists.username"));
          }
          if (customerRepository.existsByEmail(input.getEmail())) {
            throw new ValidateException(messageService.getMessage("error.code.exists.email"));
          }
          if (customerRepository.existsByPhone(input.getPhone())) {
            throw new ValidateException(messageService.getMessage("error.code.exists.phone"));
          }
        } else {
          if (customerRepository.existsByUsernameAndCustomerIdNotIn(input.getUsername(), userId)) {
            throw new ValidateException(
                messageService.getMessage("error.code.exists.username"));
          }
          if (customerRepository.existsByEmailAndCustomerIdNotIn(input.getEmail(), userId)) {
            throw new ValidateException(messageService.getMessage("error.code.exists.email"));
          }
          if (customerRepository.existsByPhoneAndCustomerIdNotIn(input.getPhone(), userId)) {
            throw new ValidateException(messageService.getMessage("error.code.exists.phone"));
          }
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }


    //change password
    @Override
    public Boolean changePassword(PasswordDTO passwordDTO) throws ValidateException {
        Customer customer = customerRepository.findById(passwordDTO.getCustomerId()).orElseThrow(
            () -> new ValidateException(messageService.getMessage("error.user.notfound")));
      if (!SecurityUtilsClient.getCustomerId().equals(customer.getCustomerId())) {
        throw new ValidateException(messageService.getMessage("error.user.not.login"));
      }
      if (passwordEncoder.matches(passwordDTO.getOldPassword(), customer.getPassword())) {
        //match successfully
        String encodedPassword = passwordEncoder.encode(passwordDTO.getNewPassword());
        if (!DataUtils.isPassword(passwordDTO.getNewPassword())) {
          throw new ValidateException(
              messageService.getMessage("error.code.invalid.password"));
        }
        customer.setPassword(encodedPassword);
        customer.setPasswordRaw(passwordDTO.getNewPassword());

        customerRepository.save(customer);
      } else {
        throw new ValidateException(messageService.getMessage("error.password.not.match"));
      }
        return true;
    }

    @Override
    public CustomerDTO forgotPassword(PasswordDTO passwordDTO) throws ValidateException {
        validateDataForgotPassword(passwordDTO);
        Customer customer = customerRepository.findCustomerByEmail(passwordDTO.getEmail());
      if (DataUtils.isNull(customer)) {
        throw new ValidateException(messageService.getMessage("error.user.notfound"));
      }
        Date now = new Date();
        if ((passwordDTO.getVerifyCode().equals(passwordDTO.getVerifyCode())) && (now.getTime()
            < customer.getCodeExpirationTime())) {
            customer.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
            customer.setPasswordRaw(passwordDTO.getNewPassword());
            customer.setVerifyCode(null);
            customerRepository.save(customer);
            return cusMapper.toDto(customer);
        } else if (!passwordDTO.getVerifyCode().equals(customer.getVerifyCode())) {
            throw new ValidateException(
                messageService.getMessage("error.code.verity.code.not.true"));
        } else {
            throw new ValidateException(
                messageService.getMessage("error.code.verify.code.expired"));
        }
    }

    @Override
    public String supportForEmail(String email) throws ValidateException {
        if (DataUtils.isNullOrEmpty(email)) {
            throw new ValidateException(messageService.getMessage("error.code.email.not.empty"));
        }

        Customer customer = customerRepository.findCustomerByEmail(email);
      if (DataUtils.isNull(customer)) {
        throw new ValidateException(messageService.getMessage("error.user.notfound"));
      }
        Long verifyCode = ThreadLocalRandom.current()
            .nextLong(DataUtils.VERIFY_CODE_FROM, DataUtils.VERIFY_CODE_TO);
        customer.setVerifyCode(verifyCode.toString());
        Date expirationTime = new Date(
            System.currentTimeMillis() + DataUtils.CODE_LIFE_TIME * 60 * 1000); //5 minute
        customer.setCodeExpirationTime(expirationTime.getTime());
        customerRepository.save(customer);
        MimeMessage message = emailSender.createMimeMessage();
        Map<String, Object> model = new HashMap<>();
        model.put("customer", customer);
        model.put("verifyCode", verifyCode);
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
            Template t = config.getTemplate("forgot-password.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            helper.setTo(customer.getEmail());
            helper.setText(html, true);
            helper.setSubject(DataUtils.EMAIL_SUBJECT_ORDER);
            helper.setFrom(DataUtils.EMAIL_SENDER);
            emailSender.send(message);

        } catch (MessagingException | IOException | TemplateException e) {
            e.getMessage();
        }
        return messageService.getMessage("success.support.email");
    }

    private void validateDataForgotPassword(PasswordDTO passwordDTO)
        throws ValidateException {
        if (DataUtils.isNullOrEmpty(passwordDTO.getEmail())) {
            throw new ValidateException(messageService.getMessage("error.code.email.not.empty"));
        }

        if (DataUtils.isNullOrEmpty(passwordDTO.getNewPassword())) {
            throw new ValidateException(messageService.getMessage("error.code.password.not.empty"));
        }
        if (!DataUtils.isPassword(passwordDTO.getNewPassword())) {
            throw new ValidateException(messageService.getMessage("error.code.password.invalid"));
        }
        if (passwordDTO.getVerifyCode().isEmpty()) {
            throw new ValidateException(
                messageService.getMessage("error.code.verify.code.not.empty"));
        }
    }
}
