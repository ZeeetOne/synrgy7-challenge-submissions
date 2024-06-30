package com.binarfud.binarfud_service.controller;

import com.binarfud.binarfud_service.dto.PasswordResetDto;
import com.binarfud.binarfud_service.dto.requests.LoginRequestDto;
import com.binarfud.binarfud_service.dto.requests.OtpVerificationRequestDto;
import com.binarfud.binarfud_service.dto.requests.PasswordResetRequestDto;
import com.binarfud.binarfud_service.dto.requests.SignupRequestDto;
import com.binarfud.binarfud_service.dto.responses.AuthenticationResponseDto;
import com.binarfud.binarfud_service.entity.accounts.User;
import com.binarfud.binarfud_service.security.jwt.JwtUtils;
import com.binarfud.binarfud_service.service.MailService;
import com.binarfud.binarfud_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Controller", description = "Authentication and user management APIs")
public class AuthController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @Autowired
    MailService mailService;


    @Operation(summary = "Register a new user", description = "Register a new user and send OTP to email for verification")
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignupRequestDto signupRequestDto) {
        userService.registerUser(signupRequestDto);
        return ResponseEntity.ok().body("OTP sent to your email. Please verify to complete registration.");
    }

    @Operation(summary = "Verify OTP", description = "Verify the OTP sent to the user's email during registration")
    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpVerificationRequestDto otpVerificationRequestDto) {
        User user = userService.activateUser(otpVerificationRequestDto.getEmail(), otpVerificationRequestDto.getOtp());
        return ResponseEntity.ok().body("User registered successfully!");
    }

    @Operation(summary = "Request password reset", description = "Request a password reset and send OTP to email for verification")
    @PostMapping("/request-password-reset")
    public ResponseEntity<String> requestPasswordReset(@RequestBody PasswordResetRequestDto passwordResetRequestDto) {
        userService.requestPasswordReset(passwordResetRequestDto.getEmail());
        return ResponseEntity.ok().body("OTP sent to your email. Please verify to reset your password.");
    }

    @Operation(summary = "Reset password", description = "Reset the user's password using the OTP sent to email")
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetDto passwordResetDto) {
        userService.resetPassword(passwordResetDto.getEmail(), passwordResetDto.getOtp(), passwordResetDto.getNewPassword());
        return ResponseEntity.ok().body("Password reset successfully!");
    }

    @Operation(summary = "Login", description = "Authenticate user and generate JWT token")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> authenticate(@RequestBody LoginRequestDto loginRequestDto) {
        AuthenticationResponseDto authResponse = userService.authenticate(loginRequestDto);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        Map<String, Object> data = new HashMap<>();
        data.put("jwt", authResponse);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Logout", description = "Logout user and invalidate JWT token")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader(value = "Authorization", required = false) String tokenHeader) {
        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>("Authorization header is missing or invalid", HttpStatus.BAD_REQUEST);
        }

        String token = tokenHeader.substring(7); // Remove "Bearer " prefix
        userService.logout(token);
        return ResponseEntity.ok("Logged out successfully!");
    }

    @Operation(summary = "OAuth2 success", description = "Handle successful OAuth2 login and generate JWT token")
    @GetMapping("/oauth2/success")
    public ResponseEntity<Map<String, Object>> googleLoginSuccess(Authentication authentication) {
        // Get the OidcUser from the authentication
        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();

        // Get roles from the database
        List<String> rolesFromDB = userService.getUserRoles(oidcUser.getEmail());
        Collection<GrantedAuthority> authorities = new ArrayList<>(oidcUser.getAuthorities());
        for (String role : rolesFromDB) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        // Create a modified OidcUser
        OidcUser modifiedOidcUser = new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());

        // Create a new Authentication object with the modified Principal
        Authentication modifiedAuthentication = new UsernamePasswordAuthenticationToken(
                modifiedOidcUser,
                oidcUser.getIdToken(),
                authorities
        );

        // Generate token using the modified authentication
        String jwt = jwtUtils.generateToken(modifiedAuthentication);

        // Extract user details from the modified authentication
        List<String> roles = modifiedAuthentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        // Prepare response
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");

        Map<String, Object> data = new HashMap<>();
        AuthenticationResponseDto jwtResponseDto = new AuthenticationResponseDto(jwt, modifiedOidcUser.getEmail(), roles);
        data.put("jwt", jwtResponseDto);

        response.put("data", data);

        // Send the token via email
        mailService.sendMail(modifiedOidcUser.getEmail(), "Your Authentication Token", "Here is your JWT token: " + jwt);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
