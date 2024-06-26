package com.example.binarfud.service;

import com.example.binarfud.model.User;
import com.example.binarfud.payload.UserDto;
import com.example.binarfud.payload.requests.UserRequestDto;
import com.example.binarfud.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Override
    public User saveUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setUsername(userRequestDto.getUsername());
        user.setEmailAddress(userRequestDto.getEmailAddress());
        user.setPassword(userRequestDto.getPassword());
        userRepository.save(user);
        return modelMapper.map(user, User.class);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }

    @Override
    public UserDto updateUser(UUID userId, UserRequestDto userRequestDto) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        existingUser.setUsername(userRequestDto.getUsername());
        existingUser.setEmailAddress(userRequestDto.getEmailAddress());
        existingUser.setPassword(userRequestDto.getPassword());
        User updatedUser = userRepository.save(existingUser);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public void deleteUser(UUID userId) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        existingUser.setDeleted(true);
        userRepository.save(existingUser);
    }
}
