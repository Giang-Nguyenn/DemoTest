package com.example.DemoTest.mapper;

import com.example.DemoTest.dto.UserDTOBasic;
import com.example.DemoTest.dto.UserDTO;
import com.example.DemoTest.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper()
public interface IUserMapper {
    IUserMapper INSTANCE= Mappers.getMapper(IUserMapper.class);
    UserDTO userToDTO(User user);
    UserDTOBasic userToDTOBasic(User user);
    List<UserDTO> userToListDTO(Page<User> user);
//    List<UserDTOBasic> userToListDTOBasic(List<User> user);
}
