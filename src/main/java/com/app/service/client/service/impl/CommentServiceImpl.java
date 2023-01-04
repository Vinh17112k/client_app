package com.app.service.client.service.impl;

import com.app.service.client.contants.Constants.STATUS;
import com.app.service.client.domain.comment.CommentDTO;
import com.app.service.client.domain.comment.CommentResponseDTO;
import com.app.service.client.domain.customer.CustomerDTO;
import com.app.service.client.domain.user.UserDTO;
import com.app.service.client.model.Comment;
import com.app.service.client.model.Customer;
import com.app.service.client.model.Notification;
import com.app.service.client.model.Product;
import com.app.service.client.repository.CommentRepository;
import com.app.service.client.repository.CustomerRepository;
import com.app.service.client.repository.NotificationRepository;
import com.app.service.client.repository.ProductRepository;
import com.app.service.client.repository.UserRepository;
import com.app.service.client.config.exceptions.ValidateException;
import com.app.service.client.config.security.SecurityUtilsClient;
import com.app.service.client.mapper.CommentClientMapper;
import com.app.service.client.mapper.CusMapper;
import com.app.service.client.mapper.ProductClientMapper;
import com.app.service.client.mapper.UserClientMapper;
import com.app.service.client.service.CommentService;
import com.app.service.client.service.MessageService;
import com.app.service.client.utils.DataUtils;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Log4j2
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MessageService messageService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CusMapper cusMapper;
    @Autowired
    private CommentClientMapper commentMapper;
    @Autowired
    private ProductClientMapper productMapper;
    @Autowired
    private UserClientMapper userClientMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    private static final String NOT_FOUND = "error.code.entity.not_found";

    @Override
    public CommentResponseDTO clientComment(CommentDTO commentDTO) throws ValidateException {
        validateCommentDTO(commentDTO);
        Product product = productRepository.findById(commentDTO.getProductId()).orElseThrow(
            () -> new ValidateException(messageService.getMessage("error.product.notfound"))
        );
        Customer client = customerRepository.findById(commentDTO.getCustomerId()).orElseThrow(
            () -> new ValidateException(messageService.getMessage("error.user.notfound"))
        );
        Comment comment = commentMapper.DtoToEntity(commentDTO);
        comment.setStatus(STATUS.ACTIVE.getCode());
        commentRepository.save(comment);
        CommentResponseDTO commentResponseDTO = commentMapper.EntityToDto(comment);
        commentResponseDTO.setCustomerDTO(cusMapper.toDto(client));
        commentResponseDTO.setProductDTO(productMapper.EntityToDto(product));
        log.debug("log to notification");
        Notification notification = new Notification();
        notification.setType(1);
        notification.setProductId(product.getId());
        notification.setCustomerId(SecurityUtilsClient.getCustomerId());
        notification.setIsComment(Boolean.TRUE);
        notification.setCommentId(comment.getCommentId());
        notification.setContent(
            messageService.getMessage("error.notification.comment", client.getUsername(),
                product.getName()));
        notificationRepository.save(notification);
        return commentResponseDTO;
    }

    private void validateCommentDTO(CommentDTO commentDTO) throws ValidateException {
        if (DataUtils.isNull(commentDTO.getProductId())) {
            throw new ValidateException(messageService.getMessage("error.comment.productId"));
        }
        if (DataUtils.isNull(commentDTO.getCustomerId())) {
            throw new ValidateException(messageService.getMessage("error.comment.clientId"));
        }
        if (DataUtils.isNull(commentDTO.getContent())) {
            throw new ValidateException(messageService.getMessage("error.comment.content"));
        }
        if (DataUtils.isNull(commentDTO.getStar())) {
            throw new ValidateException(messageService.getMessage("error.comment.star"));
        }
    }

    @Override
    public CommentResponseDTO detail(Long commentId) throws ValidateException {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new ValidateException(messageService.getMessage(NOT_FOUND)));
        CommentResponseDTO commentResponseDTO = commentMapper.EntityToDto(
            commentRepository.findById(commentId)
                .orElseThrow(() -> new ValidateException(messageService.getMessage(NOT_FOUND))));
        commentResponseDTO.setCustomerDTO(
            cusMapper.toDto(customerRepository.findById(comment.getCustomerId()).get()));
        return commentResponseDTO;
    }

    @Override
    public CommentResponseDTO update(CommentDTO commentDTO) throws ValidateException {
        Comment comment = commentMapper.DtoToEntity(commentDTO);
        comment.setStatus(1);
        commentRepository.save(comment);
        return commentMapper.EntityToDto(comment);
    }

    @Override
    public Boolean delete(Long id) throws ValidateException {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
            new ValidateException(messageService.getMessage("error.comment.notfound")));
        comment.setStatus(0);
        comment.setIsDeleted(Boolean.TRUE);
        return Boolean.TRUE;
    }

    //get all comment by product detail

    @Override
    public List<CommentResponseDTO> getCommentByProductId(Long productId) throws ValidateException {
        if (!commentRepository.getProductIds().contains(productId)) {
            return null;
        }
        List<CommentResponseDTO> commentResponseDTOS = new ArrayList<>();
        //comment goc
        List<Comment> comments = commentRepository.getAllOwnComment(productId);
        return commentDtos(commentResponseDTOS, comments);
    }

    @Override
    public List<CommentResponseDTO> getChildComment(Long productId, Long commentId)
        throws ValidateException {
        if (!commentRepository.getProductIds().contains(productId)) {
            throw new ValidateException("error.comment.product.notfound");
        }
        List<CommentResponseDTO> commentResponseDTOS = new ArrayList<>();
        //child coment
        List<Comment> comments = commentRepository.getAllChildComment(commentId);
        return commentDtos(commentResponseDTOS, comments);
    }

    private List<CommentResponseDTO> commentDtos(List<CommentResponseDTO> commentResponseDTOS,
        List<Comment> comments) {
        comments.forEach(c -> {
            CommentResponseDTO commentResponseDTO;
            commentResponseDTO = commentMapper.EntityToDto(c);
            UserDTO userDTO;
            CustomerDTO customerDTO;
            if (!DataUtils.isNull(c.getUserId())) {
                userDTO = userClientMapper.toDto(userRepository.findById(c.getUserId()).get());
                commentResponseDTO.setUserDTO(userDTO);
            }
            if (!DataUtils.isNull(c.getCustomerId())) {
                customerDTO = cusMapper.toDto(customerRepository.findById(c.getCustomerId()).get());
                commentResponseDTO.setCustomerDTO(customerDTO);
            }
            if (commentRepository.getIdChildComment(c.getProductId()).contains(c.getCommentId())) {
                commentResponseDTO.setHasChild(Boolean.TRUE);
            } else {
                commentResponseDTO.setHasChild(Boolean.FALSE);
            }
            if (!DataUtils.isNull(c.getReplyCommentId())) {
                commentResponseDTO.setIsReply(Boolean.TRUE);
                if (!DataUtils.isNull(
                    commentRepository.findById(c.getCommentId()).get().getCustomerId())) {
                    commentResponseDTO.setReplyTo(customerRepository.customerName(
                        commentRepository.findById(c.getCommentId()).get().getCustomerId()));
                }
                if (!DataUtils.isNull(
                    commentRepository.findById(c.getCommentId()).get().getUserId())) {
                    commentResponseDTO.setReplyTo(userRepository.getUserName(
                        commentRepository.findById(c.getCommentId()).get().getUserId()));
                }
            }
            commentResponseDTO.setTotalComment(commentRepository.totalComment(c.getCommentId()));
            commentResponseDTOS.add(commentResponseDTO);
        });
        return commentResponseDTOS;
    }
}