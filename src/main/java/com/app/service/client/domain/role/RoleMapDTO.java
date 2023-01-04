package com.app.service.client.domain.role;

import java.util.List;
import lombok.Data;

@Data
public class RoleMapDTO {

  private Long userId;
  private List<Long> roleId;
}
