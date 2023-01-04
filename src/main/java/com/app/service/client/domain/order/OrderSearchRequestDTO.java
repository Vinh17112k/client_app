package com.app.service.client.domain.order;

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
public class OrderSearchRequestDTO {
    private Long customerId;
    private Integer status;
    private Long fromPrice;
    private Long toPrice;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Integer hideCancel;
}
