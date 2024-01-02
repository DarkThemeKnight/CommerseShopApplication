package com.math.MathLearningWebsite.dao;

import com.math.MathLearningWebsite.entity.ApplicationUser;
import com.math.MathLearningWebsite.entity.Logging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LoggingRepository extends JpaRepository<Logging, Long> {
    List<Logging> findByUser(ApplicationUser user);
    List<Logging> findByUserAndTimestampBetween(ApplicationUser user, LocalDateTime startDate, LocalDateTime endDate);

}
