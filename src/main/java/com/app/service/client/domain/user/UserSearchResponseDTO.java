package com.app.service.client.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSearchResponseDTO implements Serializable {
  private Long userId;
  private String username;
  private String fullName;
  private String email;
  private String phone;
  private Long status;
  private String createdBy;
  private LocalDateTime createdDate;
  private String birthday;
  private LocalDateTime lastModifiedDate;
  private String roleName;
  private Integer gender;
  private Integer roleId;
}
