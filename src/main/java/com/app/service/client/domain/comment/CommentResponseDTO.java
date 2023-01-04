package com.app.service.client.domain.comment;

import com.app.service.client.domain.base.BaseDTO;
import com.app.service.client.domain.customer.CustomerDTO;
import com.app.service.client.domain.product.ProductDTO;
import com.app.service.client.domain.user.UserDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class CommentResponseDTO extends BaseDTO {
    private Long commentId;
    private Long productId;
    private UserDTO userDTO;
    private String content;
    private ProductDTO productDTO;
    private Integer status;
    private CustomerDTO customerDTO;
    private Float star;
    private CommentDTO replyComment;
    private Boolean hasChild;
    private Integer totalComment;
    private Boolean isReply;
    private String replyTo;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;
    private LocalDateTime createdDate;
}
