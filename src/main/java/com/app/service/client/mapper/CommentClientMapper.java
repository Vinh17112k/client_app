package com.app.service.client.mapper;
import com.app.service.client.domain.comment.CommentDTO;
import com.app.service.client.domain.comment.CommentEditDTO;
import com.app.service.client.domain.comment.CommentResponseDTO;
import com.app.service.client.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface CommentClientMapper {

  CommentClientMapper INSTANCE = Mappers.getMapper(CommentClientMapper.class);

  Comment DtoToEntity(CommentDTO commentDTO);
  List<CommentDTO> listEntityToDto(List<Comment> commentsd);
  Comment DtoEditToEntity(CommentEditDTO commentEditDTO);
  CommentResponseDTO EntityToDto(Comment comment);
}
