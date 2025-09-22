package org.example.userauthservice_begmay2025.utils;

import org.example.userauthservice_begmay2025.dtos.UserDto;
import org.example.userauthservice_begmay2025.models.User;

public class UserMapperUtil {
    public static UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}