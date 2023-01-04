package com.app.service.client.domain.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerSearchDTO implements Serializable {
  private String username;
  private String fullName;
  private String email;
  private String phone;
  private String idNumber;
  private Long deptId;
  private Long status;
  private Long isViettel;
  private String employeeCode;
  private Integer gender;
}
