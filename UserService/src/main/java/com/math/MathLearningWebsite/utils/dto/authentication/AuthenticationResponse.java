package com.math.MathLearningWebsite.utils.dto.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthenticationResponse {
    @JsonProperty("jwt_token")
    private String jwtToken;
    @JsonProperty("response-message")
    private String message;

    public AuthenticationResponse(String jwt_token) {
        this.jwtToken = jwt_token;
    }
}