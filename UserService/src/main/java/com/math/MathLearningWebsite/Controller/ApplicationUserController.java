package com.math.MathLearningWebsite.Controller;

import com.math.MathLearningWebsite.Services.Jwt.JwtService;
import com.math.MathLearningWebsite.Services.previleges.users.ApplicationUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user/api/v1")
public class ApplicationUserController {
    private final JwtService jwtService;
    private final ApplicationUserService applicationUserService;
    @PutMapping("/address")
    public ResponseEntity<String> updateAddress(@RequestHeader("Authorization") String authorizationHeader, @RequestParam String address) {
        String jwtToken = jwtService.extractTokenFromHeader(authorizationHeader);
        String result = applicationUserService.updateAddress(jwtToken, address);
        return ResponseEntity.ok(result);
    }
    @PutMapping("/password")
    public ResponseEntity<String> updatePassword(@RequestHeader("Authorization") String authorizationHeader, @RequestParam String newPassword) {
        String jwtToken = jwtService.extractTokenFromHeader(authorizationHeader);
        String result = applicationUserService.updatePassword(jwtToken, newPassword);
        return ResponseEntity.ok(result);
    }
    @PutMapping("/phoneNumber")
    public ResponseEntity<String> updatePhoneNumber(@RequestHeader("Authorization") String authorizationHeader, @RequestParam String phoneNumber) {
        String jwtToken = jwtService.extractTokenFromHeader(authorizationHeader);
        String result = applicationUserService.updatePhoneNumber(jwtToken, phoneNumber);
        return ResponseEntity.ok(result);
    }
    @PutMapping("/security")
    public ResponseEntity<String> updateSecurityQuestion(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam String securityQuestion,
            @RequestParam String securityAnswer
    ) {
        String jwtToken = jwtService.extractTokenFromHeader(authorizationHeader);
        String result = applicationUserService.updateSecurityQuestion(jwtToken, securityQuestion, securityAnswer);
        return ResponseEntity.ok(result);
    }
}
