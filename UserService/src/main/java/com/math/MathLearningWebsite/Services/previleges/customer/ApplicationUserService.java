package com.math.MathLearningWebsite.Services.previleges.customer;

import com.math.MathLearningWebsite.dao.ApplicationUserDao;
import com.math.MathLearningWebsite.entity.ApplicationUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplicationUserService implements UserDetailsService {
    @Autowired
    private final ApplicationUserDao userDao;
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


}
