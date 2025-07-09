package com.example.Sorokin_Event.mapper;

import com.example.Sorokin_Event.dto.UserDto;
import com.example.Sorokin_Event.dto.UserResponseDto;
import com.example.Sorokin_Event.entity.UserEntity;
import com.example.Sorokin_Event.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

public interface UserMapper {
    User toModel(UserDto dto);
    User toModel(UserEntity entity);
    UserEntity toEntity(User user);
    UserEntity toEntity(UserDto dto);
    UserDto toDto(User user);
    UserDto toDto(UserEntity entity);
    UserResponseDto toDtoResp(User user);
}
