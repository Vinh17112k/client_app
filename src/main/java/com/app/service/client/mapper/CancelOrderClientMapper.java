package com.app.service.client.mapper;

import com.app.service.client.domain.comment.CommentDTO;
import com.app.service.client.domain.comment.CommentEditDTO;
import com.app.service.client.domain.comment.CommentResponseDTO;
import com.app.service.client.domain.order.OrderCancelDTO;
import com.app.service.client.model.Comment;
import com.app.service.client.model.OrderCancel;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {})
public interface CancelOrderClientMapper {

  CancelOrderClientMapper INSTANCE = Mappers.getMapper(CancelOrderClientMapper.class);
  OrderCancelDTO EntityToDto(OrderCancel orderCancel);
}
