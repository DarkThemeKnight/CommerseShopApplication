package com.math.MathLearningWebsite.Services.previleges.admin;

import com.math.MathLearningWebsite.Services.previleges.customer.ApplicationUserService;
import com.math.MathLearningWebsite.Services.Jwt.JwtService;
import com.math.MathLearningWebsite.dao.ApplicationUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}