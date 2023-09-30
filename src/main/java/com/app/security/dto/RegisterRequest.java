package com.app.security.dto;

import com.app.security.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String userCode;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private List<Role> role;
}
