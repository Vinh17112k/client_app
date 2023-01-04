package com.app.service.client.domain.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerCreatorDTO {

  private Long customerId;
  private String username;
  private String fullName;
  private String birthday;
  private String email;
  private String phone;
  private String idNumber;
  private String issueBy;
  private String issueDate;
  private Integer status;
  private Short isViettel;
  private String password;
  private List<String> ipAddress;
  private Long isHasIpAddress;
  private MultipartFile avatar;
}
