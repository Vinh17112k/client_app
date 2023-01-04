package com.app.service.client.mapper;

import com.app.service.client.domain.user.UserCreatorDTO;
import com.app.service.client.domain.user.UserDTO;
import com.app.service.client.domain.user.UserEditRequestDTO;
import com.app.service.client.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
@Mapper(componentModel = "spring", uses = {})
public interface UserClientMapper {

    UserClientMapper INSTANCE = Mappers.getMapper(UserClientMapper.class);

    User toEntity(UserDTO userDTO);

    User toEntity(UserCreatorDTO userCreatorDTO);
    User toEntityFromUpdate(UserEditRequestDTO userEditRequestDTO);
    UserDTO toDto(User user);

    List<UserDTO> toDto(List<User> user);

    Set<UserDTO> toDto(Set<User> user);

}