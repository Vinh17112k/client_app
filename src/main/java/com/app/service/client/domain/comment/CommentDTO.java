package com.app.service.client.domain.comment;

import com.app.service.client.domain.base.BaseDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class CommentDTO extends BaseDTO {
    private Long commentId;
    private Long userId;
    private String content;
    private Long customerId;
    private Integer status;
    private Long productId;
    private Float star;
    private Long replyCommentId;
}
