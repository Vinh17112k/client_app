package com.app.service.client.controller;

import com.app.service.client.domain.comment.CommentDTO;
import com.app.service.client.domain.comment.CommentResponseDTO;
import com.app.service.client.utils.DataUtils;
import com.app.service.client.config.exceptions.GlobalExceptionHandler.ApiResponseData;
import com.app.service.client.config.exceptions.ValidateException;
import com.app.service.client.service.CartService;
import com.app.service.client.service.CommentService;
import com.app.service.client.service.OrderService;
import com.app.service.client.service.ProductClientService;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2/customer/comment")
public class CommentController {

    @Autowired
    private ProductClientService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CommentService commentService;

    @PostMapping("/add-comment")
    public ResponseEntity<ApiResponseData<CommentResponseDTO>> comment(
        @RequestBody CommentDTO commentDTO)
        throws ValidateException {
        return ResponseEntity.ok(ApiResponseData.<CommentResponseDTO>builder()
            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
            .messages(Arrays.asList("success"))
            .data(commentService.clientComment(commentDTO))
            .build());
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<ApiResponseData<CommentResponseDTO>> orderDetail(
        @PathVariable("id") Long commentId)
        throws ValidateException {
        return ResponseEntity.ok(ApiResponseData.<CommentResponseDTO>builder()
            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
            .messages(Arrays.asList("success"))
            .data(commentService.detail(commentId))
            .build());
    }

    @GetMapping("get-all")
    public ResponseEntity<ApiResponseData<List<CommentResponseDTO>>> getCommentByProductId(
        @RequestParam("productId") Long productId)
        throws ValidateException {
        return ResponseEntity.ok(ApiResponseData.<List<CommentResponseDTO>>builder()
            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
            .messages(Arrays.asList("success"))
            .data(commentService.getCommentByProductId(productId))
            .build());
    }

    @PostMapping("update")
    public ResponseEntity<ApiResponseData<CommentResponseDTO>> update(
        @RequestBody CommentDTO commentDTO)
        throws ValidateException {
        return ResponseEntity.ok(ApiResponseData.<CommentResponseDTO>builder()
            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
            .messages(Arrays.asList("success"))
            .data(commentService.update(commentDTO))
            .build());
    }

    @DeleteMapping("deleted/{id}")
    public ResponseEntity<ApiResponseData<Boolean>> deleteComment(@PathVariable("id") Long id)
        throws ValidateException {
        return ResponseEntity.ok(ApiResponseData.<Boolean>builder()
            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
            .messages(Arrays.asList("success"))
            .data(commentService.delete(id))
            .build());
    }

    @GetMapping("get-all-child")
    public ResponseEntity<ApiResponseData<List<CommentResponseDTO>>> getCommentByProductId(
        @RequestParam("productId") Long productId, @RequestParam("commentId") Long commentId)
        throws ValidateException {
        return ResponseEntity.ok(ApiResponseData.<List<CommentResponseDTO>>builder()
            .errorCode(DataUtils.safeToString(HttpStatus.OK.value()))
            .messages(Arrays.asList("success"))
            .data(commentService.getChildComment(productId, commentId))
            .build());
    }
}