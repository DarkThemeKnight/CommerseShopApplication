package com.math.MathLearningWebsite.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
public class Logging {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser user;

    @Column(nullable = false)
    private String logMessage;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public Logging() {
        this.timestamp = LocalDateTime.now();
    }

    public Logging(ApplicationUser user, String logMessage) {
        this.user = user;
        this.logMessage = logMessage;
        this.timestamp = LocalDateTime.now();
    }


}