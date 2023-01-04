package com.app.service.client.domain.notification;

import com.app.service.client.domain.customer.CustomerDTO;
import com.app.service.client.domain.product.ProductDTO;
import com.app.service.client.domain.user.UserDTO;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private CustomerDTO customerDTO;
    private ProductDTO productDTO;
    private UserDTO userDTO;
    private Integer type;
    private String content;
    private Boolean isComment;
    private Long commentId;
    private LocalDateTime createdDate;
}
