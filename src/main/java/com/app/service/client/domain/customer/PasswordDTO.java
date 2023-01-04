package com.app.service.client.domain.customer;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordDTO implements Serializable {
  private String oldPassword;
  private String newPassword;
  private Long customerId;

//  for send to email
  private String email;
  private String verifyCode;

}
