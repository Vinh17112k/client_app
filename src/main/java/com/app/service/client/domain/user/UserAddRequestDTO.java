package com.app.service.client.domain.user;

import com.app.service.client.domain.role.RoleDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAddRequestDTO extends UserBaseDTO {
    private Set<RoleDTO> roles;
}
