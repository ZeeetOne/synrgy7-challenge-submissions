package com.example.binarfud.service;

import com.example.binarfud.entity.accounts.ERole;
import com.example.binarfud.entity.accounts.Role;
import com.example.binarfud.entity.accounts.User;
import com.example.binarfud.dto.UserDto;
import com.example.binarfud.dto.requests.SignupRequestDto;
import com.example.binarfud.dto.requests.UserRequestDto;
import com.example.binarfud.repository.RoleRepository;
import com.example.binarfud.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

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

    @Override
    public User registerUser(SignupRequestDto signupRequestDto) {
        if (userRepository.existsByUsername(signupRequestDto.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }

        if (userRepository.existsByEmailAddress(signupRequestDto.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }

        User user = new User();
        user.setUsername(signupRequestDto.getUsername());
        user.setEmailAddress(signupRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER);
        roles.add(userRole);
        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public void createUserPostLogin(String username, String email) {
        Role role = roleRepository.findByName(ERole.ROLE_USER);
        Set<Role> roles = new HashSet<>(Collections.singletonList(role));

        User user = getByUsername(email);
        if(user == null){
            user = User.builder()
                    .username(username)
                    .emailAddress(email)
                    .roles(roles)
                    .build();
            userRepository.save(user);
        }
    }

    @Override
    public List<String> getUserRoles(String email) {
        return userRepository.findRolesByEmail(email);
    }

    private User getByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.orElse(null);
    }
}
