package com.example.DemoTest.mapper;

import com.example.DemoTest.dto.UserDTO;
import com.example.DemoTest.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface IUserMapper {
    IUserMapper INSTANCE= Mappers.getMapper(IUserMapper.class);
    UserDTO userToDTO(User user);
}
