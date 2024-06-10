package com.example.binarfud.service;

import com.example.binarfud.model.User;
import com.example.binarfud.payload.UserDto;
import com.example.binarfud.payload.requests.UserRequestDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User saveUser(UserRequestDto userRequestDto);
    List<User> getUsers();
    User getUser(UUID usertId);
    UserDto updateUser(UUID userId, UserRequestDto userRequestDto);
    void deleteUser(UUID userId);
}
