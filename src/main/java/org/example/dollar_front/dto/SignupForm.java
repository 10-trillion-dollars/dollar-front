package org.example.dollar_front.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupForm {
    private String email;
    private String username;
    private String password;
    private boolean admin;
    private String adminToken;
}

