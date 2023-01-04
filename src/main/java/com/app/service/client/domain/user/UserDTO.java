package com.app.service.client.domain.user;

import com.app.service.client.domain.role.RoleDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.util.Set;
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
public class UserDTO {
  private Long userId;
  private String username;
  private Long deptId;
  private String fullName;
  private String birthday;
  private String email;
  private String phone;
  private String idNumber;
  private String issueBy;
  private String issueDate;
  private String passwordRaw;
  private Long status;
  private String createdBy;
  private LocalDateTime createdDate;
  private String lastModifiedBy;
  private LocalDateTime lastModifiedDate;
  private Set<RoleDTO> roles;
  private String deptName;
  private Short allowReceiveExcel;
  private Short allowReceivePdf;
  private String employeeCode;

}
