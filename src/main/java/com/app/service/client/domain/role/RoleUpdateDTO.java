package com.app.service.client.domain.role;

import lombok.Data;

@Data
public class RoleUpdateDTO {

  private Long roleId;
  private String name;
  private String description;
  private Short rowStatus;
}
