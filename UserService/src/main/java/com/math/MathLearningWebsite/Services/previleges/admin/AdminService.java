package com.math.MathLearningWebsite.Services.previleges.admin;

import com.math.MathLearningWebsite.Services.previleges.users.ApplicationUserService;
import com.math.MathLearningWebsite.dao.ApplicationUserDao;
import com.math.MathLearningWebsite.entity.ApplicationUser;
import com.math.MathLearningWebsite.enumerations.Message;
import com.math.MathLearningWebsite.enumerations.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final ApplicationUserService userService;
    private final ApplicationUserDao userDao;

    @Autowired
    public AdminService(ApplicationUserService userService, ApplicationUserDao userDao) {
        this.userService = userService;
        this.userDao = userDao;

    }

    public String lockUserAccount(String email){
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
    public String unlockUserAccount(String email){
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
    public String deleteAccount(String email){
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
    public String updateRole(String email, Role role){
        if (role == null){
            return "No null parameters";
        }
        ApplicationUser user;
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