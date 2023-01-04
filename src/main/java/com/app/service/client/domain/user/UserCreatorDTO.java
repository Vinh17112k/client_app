package com.app.service.client.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCreatorDTO extends UserBaseDTO {
    @NotBlank(message = "Mật khẩu ")
    @Size(min = 8)
    private String password;
//    @NotBlank(message = "Quyền ")
    private Set<Long> roleIds;
    private Long roleId;
}
