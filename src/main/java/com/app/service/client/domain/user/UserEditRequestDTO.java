package com.app.service.client.domain.user;

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
public class UserEditRequestDTO extends UserBaseDTO {
//    @NotBlank(message = "error.comment.clientId")
//    @PositiveOrZero(message = "Không được ngoài ký tự số")
    private Long userId;
//    @NotNull(message = "Quyền")
    private Set<Long> roleIds;
    private Long roleId;
}
