package com.binarfud.binarfud_service.service;

import com.binarfud.binarfud_service.dto.UserDto;
import com.binarfud.binarfud_service.dto.requests.LoginRequestDto;
import com.binarfud.binarfud_service.dto.requests.SignupRequestDto;
import com.binarfud.binarfud_service.dto.requests.UserRequestDto;
import com.binarfud.binarfud_service.dto.responses.AuthenticationResponseDto;
import com.binarfud.binarfud_service.entity.accounts.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User saveUser(UserRequestDto userRequestDto);
    List<User> getUsers();
    User getUser(UUID usertId);
    UserDto updateUser(UUID userId, UserRequestDto userRequestDto);
    void deleteUser(UUID userId);

    void registerUser(SignupRequestDto signupRequestDto);
    User activateUser(String email, String otp);
    void requestPasswordReset(String email);
    void resetPassword(String email, String otp, String newPassword);

    void createUserPostLogin(String name, String email);

    public List<String> getUserRoles(String email);

    AuthenticationResponseDto authenticate(LoginRequestDto loginRequestDto);
    void logout(String token);
}
