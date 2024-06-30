package com.binarfud.binarfud_service.service;

import com.binarfud.binarfud_service.dto.UserDto;
import com.binarfud.binarfud_service.dto.requests.LoginRequestDto;
import com.binarfud.binarfud_service.dto.requests.SignupRequestDto;
import com.binarfud.binarfud_service.dto.requests.UserRequestDto;
import com.binarfud.binarfud_service.dto.responses.AuthenticationResponseDto;
import com.binarfud.binarfud_service.entity.Token;
import com.binarfud.binarfud_service.entity.accounts.ERole;
import com.binarfud.binarfud_service.entity.accounts.Role;
import com.binarfud.binarfud_service.entity.accounts.User;
import com.binarfud.binarfud_service.repository.RoleRepository;
import com.binarfud.binarfud_service.repository.TokenRepository;
import com.binarfud.binarfud_service.repository.UserRepository;
import com.binarfud.binarfud_service.security.jwt.JwtUtils;
import com.binarfud.binarfud_service.security.service.UserDetailsImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

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

    private static final long OTP_EXPIRATION_MINUTES = 5;

    @Override
    public void registerUser(SignupRequestDto signupRequestDto) {
        if (userRepository.existsByUsername(signupRequestDto.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }

        if (userRepository.existsByEmailAddress(signupRequestDto.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }

        String otp = generateOtp();

        User user = new User();
        user.setUsername(signupRequestDto.getUsername());
        user.setEmailAddress(signupRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER);
        roles.add(userRole);
        user.setRoles(roles);

        user.setVerified(false);
        user.setOtp(otp);
        user.setOtpExpirationTime(LocalDateTime.now().plusMinutes(OTP_EXPIRATION_MINUTES));

        userRepository.save(user);
        sendOtpEmail(signupRequestDto.getEmail(), otp);
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = random.nextInt(1000000); // Generates a number between 0 and 999999
        return String.format("%06d", otp); // Ensures the OTP is zero-padded to 6 digits
    }

    private void sendOtpEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp);
        javaMailSender.send(message);
    }

    @Override
    public User activateUser(String email, String otp) {
        User user = userRepository.findByEmailAddress(email)
                .orElseThrow(() -> new RuntimeException("No registration found with this email"));

        if (user.getOtp().equals(otp) && !user.isVerified()) {
            if (user.getOtpExpirationTime().isAfter(LocalDateTime.now())) {
                user.setVerified(true);
                user.setOtp(null); // Clear OTP after successful verification
                user.setOtpExpirationTime(null); // Clear OTP expiration time
                return userRepository.save(user);
            } else {
                throw new RuntimeException("OTP has expired");
            }
        } else {
            throw new RuntimeException("Invalid OTP or already verified");
        }
    }

    @Override
    public void requestPasswordReset(String email) {
        User user = userRepository.findByEmailAddress(email)
                .orElseThrow(() -> new RuntimeException("No user found with this email"));

        String otp = generateOtp();
        user.setOtp(otp);
        userRepository.save(user);
        sendOtpEmail(email, otp);
    }

    @Override
    public void resetPassword(String email, String otp, String newPassword) {
        User user = userRepository.findByEmailAddress(email)
                .orElseThrow(() -> new RuntimeException("No user found with this email"));

        if (user.getOtp().equals(otp)) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setOtp(null); // Clear OTP after successful reset
            userRepository.save(user);
        } else {
            throw new RuntimeException("Invalid OTP");
        }
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

    @Override
    public AuthenticationResponseDto authenticate(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword()
                ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        Instant expiryDate = Instant.now().plusMillis(jwtUtils.getJwtExpirationMs());

        Token token = new Token();
        token.setUser(user);
        token.setToken(jwt);
        token.setExpiryDate(expiryDate);
        tokenRepository.save(token);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new AuthenticationResponseDto(jwt, userDetails.getUsername(), roles);
    }

    @Override
    @Transactional
    public void logout(String token) {
        tokenRepository.deleteByToken(token);
    }

    private User getByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.orElse(null);
    }
}
