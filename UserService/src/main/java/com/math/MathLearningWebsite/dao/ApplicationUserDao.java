package com.math.MathLearningWebsite.dao;

import com.math.MathLearningWebsite.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApplicationUserDao extends JpaRepository<ApplicationUser, UUID> {
    Optional<ApplicationUser> findByEmail(String username);
}
