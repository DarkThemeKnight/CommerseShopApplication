package com.math.MathLearningWebsite.Services.previleges.admin;

import com.math.MathLearningWebsite.Services.previleges.customer.ApplicationUserService;
import com.math.MathLearningWebsite.Services.Jwt.JwtService;
import com.math.MathLearningWebsite.dao.ApplicationUserDao;
import com.math.MathLearningWebsite.entity.ApplicationUser;
import com.math.MathLearningWebsite.enumerations.Message;
import com.math.MathLearningWebsite.enumerations.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {
    private final ApplicationUserService userService;
    private final ApplicationUserDao userDao;
    private final JwtService jwtService;
    @Autowired
    public AdminService(ApplicationUserService userService, ApplicationUserDao userDao, JwtService jwtService) {
        this.userService = userService;
        this.userDao = userDao;
        this.jwtService = jwtService;
    }

    private String lockUserAccount(String email){
        ApplicationUser user;
        try {
              user= userService.loadUserByUsername(email);
              if (user.getUserAuthorities() == Role.ADMIN || user.getUserAuthorities() == Role.SUPER_ADMIN){
                  return Message.UNAUTHORIZED_ACCESS.getMessage();
              }
        }catch (UsernameNotFoundException e){
            return Message.USER_NOT_FOUND.getMessage();
        }
        user.setAccountNonLocked(false);
        userDao.save(user);
        return "Success";
    }
    private String unlockUserAccount(String email){
            ApplicationUser user;
            try {
                user= userService.loadUserByUsername(email);
                if (user.getUserAuthorities() == Role.ADMIN || user.getUserAuthorities() == Role.SUPER_ADMIN){
                    return Message.UNAUTHORIZED_ACCESS.getMessage();
                }
            }catch (UsernameNotFoundException e){
                return Message.USER_NOT_FOUND.getMessage();
            }
            user.setAccountNonLocked(true);
            userDao.save(user);
            return "Success";
    }
    private String deleteAccount(String email){
        ApplicationUser user;
        try {
            user= userService.loadUserByUsername(email);
            if (user.getUserAuthorities() == Role.ADMIN || user.getUserAuthorities() == Role.SUPER_ADMIN){
                return Message.UNAUTHORIZED_ACCESS.getMessage();
            }
        }catch (UsernameNotFoundException e){
            return Message.USER_NOT_FOUND.getMessage();
        }
        userDao.deleteById(user.getUserId());
        return "Success";
    }
    private String updateRole(String email, Role role){
        if (role == null){
            return "No null parameters";
        }
        ApplicationUser user;
            user= userService.loadUserByUsername(email);
            try {
                user= userService.loadUserByUsername(email);
                if (role == Role.SUPER_ADMIN || role == Role.ADMIN || user.getUserAuthorities() == Role.ADMIN || user.getUserAuthorities() == Role.SUPER_ADMIN){
                    return Message.UNAUTHORIZED_ACCESS.getMessage();
                }
            }catch (UsernameNotFoundException e){
                return Message.USER_NOT_FOUND.getMessage();
            }
            user.setUserAuthorities(role);
            userDao.save(user);
            return "Success";
    }




}