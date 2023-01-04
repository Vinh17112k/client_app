package com.app.service.client.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSearchDTO implements Serializable {
  private String username;
  private String fullName;
  private String email;
  private String phone;
  private Long status;
  private Long roleId;
  private Integer gender;
}
