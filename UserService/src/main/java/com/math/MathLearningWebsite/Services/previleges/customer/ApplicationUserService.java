package com.math.MathLearningWebsite.Services.previleges.customer;

import com.math.MathLearningWebsite.Services.Jwt.JwtService;
import com.math.MathLearningWebsite.dao.ApplicationUserDao;
import com.math.MathLearningWebsite.entity.ApplicationUser;
import com.math.MathLearningWebsite.enumerations.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplicationUserService implements UserDetailsService {
    @Autowired
    private final ApplicationUserDao userDao;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Override
    public ApplicationUser loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ApplicationUser> applicationUser = userDao.findByEmail(username);
        if (applicationUser.isEmpty()){
            log.debug("User not found");
            throw new UsernameNotFoundException("User "+username+" is not found");
        }
        log.debug("User found");
        return applicationUser.get();
    }
    public String updateAddress(String jwtToken, String address){
        String username = jwtService.extractMail(jwtToken); // Extract username from token
        ApplicationUser user = userDao.findByEmail(username).orElse(null); // Retrieve user from database
        if (user != null) {
            user.setAddress(address); // Update user's address
            userDao.save(user); // Save updated user details to the database
            return "Address updated successfully";
        } else {
            return Message.USER_NOT_FOUND.getMessage();
        }
    }

    public String updatePassword(String jwtToken, String newPassword){
        String username = jwtService.extractMail(jwtToken); // Extract username from token
        ApplicationUser user = userDao.findByEmail(username).orElse(null); // Retrieve user from database

        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword)); // Encode and set new password
            userDao.save(user); // Save updated user details to the database
            return "Password updated successfully";
        } else {
            return Message.USER_NOT_FOUND.getMessage();
        }
    }
    public String updatePhoneNumber(String jwtToken, String phoneNumber){
        String username = jwtService.extractMail(jwtToken); // Extract username from token
        ApplicationUser user = userDao.findByEmail(username).orElse(null); // Retrieve user from database
        if (user != null) {
            user.setPhoneNumber(phoneNumber); // Update user's phone number
            userDao.save(user); // Save updated user details to the database
            return "Phone number updated successfully";
        } else {
            return "User not found";
        }
    }
    public String updateSecurityQuestion(String jwtToken, String securityQuestion, String securityAnswer){
        String username = jwtService.extractMail(jwtToken); // Extract username from token
        ApplicationUser user = userDao.findByEmail(username).orElse(null); // Retrieve user from database
        if (user != null) {
            user.setSecurityQuestion(securityQuestion); // Update user's security question
            user.setSecurityAnswer(securityAnswer); // Update user's security answer
            userDao.save(user); // Save updated user details to the database
            return "Security question and answer updated successfully";
        } else {
            return "User not found";
        }
    }

}
