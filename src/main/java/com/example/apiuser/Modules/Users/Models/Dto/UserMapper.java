package com.example.apiuser.Modules.Users.Models.Dto;

import com.example.apiuser.Modules.Users.Models.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserResponseDTO toDTO(User user);
    User toEntity(UserRequestDTO userRequestDTO);
}