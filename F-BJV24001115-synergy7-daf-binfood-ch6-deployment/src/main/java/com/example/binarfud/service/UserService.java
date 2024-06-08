package com.example.binarfud.service;

import com.example.binarfud.entity.accounts.User;
import com.example.binarfud.dto.UserDto;
import com.example.binarfud.dto.requests.SignupRequestDto;
import com.example.binarfud.dto.requests.UserRequestDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User saveUser(UserRequestDto userRequestDto);
    List<User> getUsers();
    User getUser(UUID usertId);
    UserDto updateUser(UUID userId, UserRequestDto userRequestDto);
    void deleteUser(UUID userId);

    User registerUser(SignupRequestDto signupRequestDto);

    void createUserPostLogin(String name, String email);
}
