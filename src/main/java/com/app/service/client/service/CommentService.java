package com.app.service.client.service;

import com.app.service.client.config.exceptions.ValidateException;
import com.app.service.client.domain.comment.CommentDTO;
import com.app.service.client.domain.comment.CommentResponseDTO;

import java.util.List;

public interface CommentService {

    CommentResponseDTO clientComment(CommentDTO commentDTO) throws ValidateException;

    CommentResponseDTO detail(Long commentId) throws ValidateException;

    CommentResponseDTO update(CommentDTO commentDTO) throws ValidateException;

    Boolean delete(Long id) throws ValidateException;

    List<CommentResponseDTO> getCommentByProductId(Long productId) throws ValidateException;

    List<CommentResponseDTO> getChildComment(Long productId, Long commentId)
        throws ValidateException;

}