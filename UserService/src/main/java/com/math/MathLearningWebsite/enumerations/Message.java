package com.math.MathLearningWebsite.enumerations;

public enum Message {
    USER_NOT_FOUND("User not found."),
    INVALID_CREDENTIALS("Invalid credentials provided."),
    ACCOUNT_LOCKED("Your account has been locked. Please contact support."),
    PASSWORD_RESET_SUCCESS("Password reset successful."),
    PASSWORD_RESET_LINK_EXPIRED("The password reset link has expired."),
    PASSWORD_WEAK("Password does not meet the security requirements."),
    UNAUTHORIZED_ACCESS("Unauthorized access detected."),
    INVALID_EMAIL("Email is invalid."),
    USER_EXISTS("User already exists"),
    USER_CREATED_SUCCESSFULLY("user successfully created");
    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
