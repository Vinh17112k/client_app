package com.app.service.client.domain.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

  private Long roleId;
  private String code;
  private String name;
  private String description;
  private Integer status;
}
