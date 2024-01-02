package com.math.MathLearningWebsite.Services.authentication;
import com.google.common.util.concurrent.RateLimiter;
import com.math.MathLearningWebsite.Services.Jwt.JwtService;
import com.math.MathLearningWebsite.dao.ApplicationUserDao;
import com.math.MathLearningWebsite.entity.ApplicationUser;
import com.math.MathLearningWebsite.enumerations.Message;
import com.math.MathLearningWebsite.enumerations.Role;
import com.math.MathLearningWebsite.utils.dto.authentication.AuthenticationRequest;
import com.math.MathLearningWebsite.utils.dto.authentication.AuthenticationResponse;
import com.math.MathLearningWebsite.utils.validation.UsernamePasswordChecking;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Slf4j
@Service
public class AuthenticationService {
    private final RateLimiter rateLimiter = RateLimiter.create(1);
    private final ApplicationUserDao applicationUserDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    @Autowired
    public AuthenticationService(ApplicationUserDao applicationUserDao,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService) {
        this.applicationUserDao = applicationUserDao;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthenticationResponse registerUser(AuthenticationRequest request) {
        if (!rateLimiter.tryAcquire()){
            return new AuthenticationResponse(null, "Too many requests. Please try again later.");
        }
        if (invalidCredentials(request)) {
            return new AuthenticationResponse(null, Message.INVALID_CREDENTIALS.getMessage());
        }

        if (!UsernamePasswordChecking.isValidEmail(request.getEmail())) {
            return new AuthenticationResponse(null, Message.INVALID_EMAIL.getMessage());
        }
        if (!UsernamePasswordChecking.isValidPassword(request.getPassword())) {
            return new AuthenticationResponse(null, Message.PASSWORD_WEAK.getMessage());
        }
        Optional<ApplicationUser> userIfExists = applicationUserDao.findByEmail(request.getEmail());
        if (userIfExists.isPresent()) {
            return new AuthenticationResponse(null, Message.USER_EXISTS.getMessage());
        }
        ApplicationUser toSave = buildUserFromRequest(request);
        ApplicationUser response = applicationUserDao.save(toSave);
        String token = jwtService.generate(new HashMap<>(), response);
        return new AuthenticationResponse(token);
    }
    private boolean invalidCredentials(AuthenticationRequest request) {
        return request == null ||
                request.getEmail() == null ||
                request.getPassword() == null ||
                request.getLastName() == null ||
                request.getAddress() == null ||
                request.getSecurityAnswer() == null ||
                request.getSecurityQuestion() == null ||
                request.getFirstName() == null ||
                request.getPhoneNumber() == null;
    }

    private ApplicationUser buildUserFromRequest(AuthenticationRequest request) {
        return ApplicationUser.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .securityQuestion(request.getSecurityQuestion())
                .securityAnswer(request.getSecurityAnswer())
                .userAuthorities(Role.CUSTOMER)
                .isAccountNonLocked(true)
                .isEnabled(true)
                .isCredentialsNonExpired(true)
                .isAccountNonExpired(true)
                .build();
    }

    public AuthenticationResponse loginUser(AuthenticationRequest request) {
        if (!UsernamePasswordChecking.isValidEmail(request.getEmail())){
            return new AuthenticationResponse(null, Message.INVALID_EMAIL.getMessage());
        }
        ApplicationUser user = applicationUserDao.findByEmail(request.getEmail()).orElse(null);
        if (user == null || !passwordEncoder.matches(request.getPassword(),user.getPassword())){
            return new AuthenticationResponse(null,
                    Message.INVALID_CREDENTIALS.getMessage());
        }
        if (!user.isAccountNonExpired()) return new AuthenticationResponse(null,Message.ACCOUNT_LOCKED.getMessage());
        if (!user.isCredentialsNonExpired()){
            user.setCredentialsNonExpired(true);
            user = applicationUserDao.save(user);
        }
        return new AuthenticationResponse(jwtService.generate(new HashMap<>(),user));
    }
    public String logout(String jwtToken){
        String email = jwtService.extractMail(jwtToken);
        ApplicationUser user = applicationUserDao.findByEmail(email).orElse(null);
        if (user == null){
            return Message.USER_NOT_FOUND.getMessage();
        }
        user.setCredentialsNonExpired(false);
        applicationUserDao.save(user);
        return "LOGGED OUT SUCCESSFULLY";
    }




 }