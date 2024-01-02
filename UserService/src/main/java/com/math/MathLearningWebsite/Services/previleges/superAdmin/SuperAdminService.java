package com.math.MathLearningWebsite.Services.previleges.superAdmin;

import com.math.MathLearningWebsite.dao.ApplicationUserDao;
import com.math.MathLearningWebsite.entity.ApplicationUser;
import com.math.MathLearningWebsite.enumerations.Message;
import com.math.MathLearningWebsite.enumerations.Role;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@AllArgsConstructor
public class SuperAdminService {
    @Autowired
    private final ApplicationUserDao userDao;
    public String updateUserRole(Role role, String email){
        if (role == Role.SUPER_ADMIN) return "Failed";
        Optional<ApplicationUser> userOptional = userDao.findByEmail(email);
        if (userOptional.isPresent()) {
            ApplicationUser user = userOptional.get();
            user.setUserAuthorities(role);
            userDao.save(user);
            return "Success";
        }
        return Message.USER_NOT_FOUND.getMessage();
    }
    public String lockAdmin(String email) {
        Optional<ApplicationUser> userOptional = userDao.findByEmail(email);
        if (userOptional.isPresent()) {
            ApplicationUser adminUser = userOptional.get();
            if (adminUser.getUserAuthorities() == Role.ADMIN) {
                adminUser.setAccountNonLocked(false); // Assuming ApplicationUser has a 'locked' field
                userDao.save(adminUser);
                return "Admin locked successfully.";
            } else {
                return "User is not an admin.";
            }
        }
        return "User not found.";
    }
    public String unLockAdmin(String email) {
        Optional<ApplicationUser> userOptional = userDao.findByEmail(email);
        if (userOptional.isPresent()) {
            ApplicationUser adminUser = userOptional.get();
            if (adminUser.getUserAuthorities() == Role.ADMIN) {
                adminUser.setAccountNonLocked(true); // Assuming ApplicationUser has a 'locked' field
                userDao.save(adminUser);
                return "Admin locked successfully.";
            } else {
                return "User is not an admin.";
            }
        }
        return "User not found.";
    }
    public String addAdmin(String email) {
        Optional<ApplicationUser> userOptional = userDao.findByEmail(email);
        if (userOptional.isPresent()){
            ApplicationUser user = userOptional.get();
            user.setUserAuthorities(Role.ADMIN);
            userDao.save(user);
            return "Admin added";
        }
        return "User not found";
    }



}