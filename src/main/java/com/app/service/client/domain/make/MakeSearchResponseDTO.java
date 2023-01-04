package com.app.service.client.domain.make;

import com.app.service.client.domain.base.BaseDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
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
public class MakeSearchResponseDTO extends BaseDTO {
    private Long id;
    private String name;
    private String code;
    private String createdBy;
    private LocalDateTime lastModifiedDate;
}
