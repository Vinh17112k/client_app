package com.app.service.client.config.security;
import com.app.service.client.model.Customer;
import com.app.service.client.config.oauth2.UserPrincipal;
import java.math.BigInteger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtilsClient {

  public static String getUserLogin() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      return "";
    }
    if (authentication.getPrincipal() instanceof Customer) {
      Customer user = (Customer) authentication.getPrincipal();
      if (user != null) {
        return user.getUsername();
      }
    }
    return authentication.getPrincipal().toString();
  }
  public static Customer getCustomer() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      return new Customer();
    }
    Object obj = authentication.getPrincipal();
    if (obj instanceof Customer) {
      Customer user = (Customer) obj;
      if (user != null) {
        return user;
      }
    }
    return new Customer();
  }
  public static Long getCustomerId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      return null;
    }
    Object obj = authentication.getPrincipal();
    if (obj instanceof UserPrincipal) {
      return ((UserPrincipal) obj).getCustomerId();
    }
    return null;
  }
  public static String getUsername() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      return new String();
    }
    Object obj = authentication.getPrincipal();
    if (obj instanceof UserPrincipal) {
      return ((UserPrincipal) obj).getUsername();
    }
    return new String();
  }
}