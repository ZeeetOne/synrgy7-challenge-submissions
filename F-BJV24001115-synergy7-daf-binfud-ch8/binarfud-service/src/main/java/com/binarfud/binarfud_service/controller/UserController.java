package com.binarfud.binarfud_service.controller;

import com.binarfud.binarfud_service.dto.UserDto;
import com.binarfud.binarfud_service.dto.requests.UserRequestDto;
import com.binarfud.binarfud_service.dto.responses.UserResponseDto;
import com.binarfud.binarfud_service.entity.accounts.User;
import com.binarfud.binarfud_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Controller", description = "User management APIs")
public class UserController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Operation(summary = "Save a user", description = "Save a new user")
    @PostMapping
    public ResponseEntity<Map<String, Object>> saveUser(@RequestBody UserRequestDto userRequestDto) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        Map<String, Object> data = new HashMap<>();
        User user = userService.saveUser(userRequestDto);
        UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);
        data.put("user", userResponseDto);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get users", description = "Retrieve users")
    @GetMapping
    public ResponseEntity<Map<String, Object>> getUsers() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        Map<String, Object> data = new HashMap<>();
        List<User> userList = userService.getUsers();
        List<UserResponseDto> userResponseDtoList = userList.stream()
                        .map(user -> modelMapper.map(user, UserResponseDto.class))
                        .toList();
        data.put("user", userResponseDtoList);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get user by ID", description = "Retrieve a user by its ID")
    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getUser(@PathVariable("userId") UUID userId) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        Map<String, Object> data = new HashMap<>();
        User user = userService.getUser(userId);
        UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);
        data.put("user", userResponseDto);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Update user", description = "Update an existing user")
    @PutMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable("userId") UUID userId, @RequestBody UserRequestDto userRequestDto) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        Map<String, Object> data = new HashMap<>();
        UserDto updateUser = userService.updateUser(userId, userRequestDto);
        UserResponseDto userResponseDto = modelMapper.map(updateUser, UserResponseDto.class);
        data.put("user", userResponseDto);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Delete user", description = "Delete a user by its ID")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable("userId") UUID userId) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        userService.deleteUser(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
