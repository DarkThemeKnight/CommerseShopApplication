package com.math.MathLearningWebsite.Controller;

import com.math.MathLearningWebsite.Services.Jwt.JwtService;
import com.math.MathLearningWebsite.Services.authentication.AuthenticationService;
import com.math.MathLearningWebsite.utils.dto.authentication.AuthenticationRequest;
import com.math.MathLearningWebsite.utils.dto.authentication.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/auth/v1")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, JwtService service) {
        this.authenticationService = authenticationService;
        this.jwtService = service;
    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.registerUser(request);
        if (response.getJwtToken() != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.loginUser(request);
        if (response.getJwtToken() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
    @PostMapping("/logout")
    public String logout(@RequestHeader("Authorization") String authorizationHeader){
        String jwtToken = jwtService.extractTokenFromHeader(authorizationHeader);
        return authenticationService.logout(jwtToken);
    }

}