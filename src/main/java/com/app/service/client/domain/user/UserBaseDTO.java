package com.app.service.client.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class UserBaseDTO implements Serializable {
  @NotBlank(message = "Tên  ")
  @Size(max = 20)
  private String username;
  @NotBlank(message = "Họ tên người dùng ")
  @Size(max = 255)
  private String fullName;
  private String birthday;
  @NotBlank(message = "Email người dùng ")
  @Size(max = 50)
  private String email;
  @Size(min = 10)
  @NotBlank(message = "Số điện thoại người dùng ")
  private String phone;
  private String idNumber;
  private String issueBy;
  private String issueDate;
  private Integer status;
  private Integer gender;
}
