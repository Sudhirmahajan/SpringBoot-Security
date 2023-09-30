package com.app.security.service;

import com.app.security.domain.user.*;
import com.app.security.dto.AuthenticationRequest;
import com.app.security.dto.AuthenticationResponse;
import com.app.security.dto.RegisterRequest;
import com.app.security.dto.UserDTO;
import com.app.security.repository.users.PrivilegeRepository;
import com.app.security.repository.users.TokenRepository;
import com.app.security.repository.users.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PrivilegeRepository privilegeRepository;

    public AuthenticationResponse register(RegisterRequest payload) {
    /*var user = User.builder()
            .userCode(payload.getUserCode())
            .firstName(payload.getFirstname())
            .lastName(payload.getLastname())
            .email(payload.getEmail())
            .password(passwordEncoder.encode(payload.getPassword()))
            .roles(payload.getRole())
            .build();*/
        User user = new User();

        List<String> privileges = Arrays.asList("TEST:PRIVILEGE");
        //privilegeRepository.findAll().stream().map(Privilege::getPermission).distinct().collect(Collectors.toList());
        List<Role> roleList = new ArrayList<>();
        for (Role rol : payload.getRole()) {
            Role roles = new Role();
            roles.setName(rol.getName());
            // roles.setUsers(Arrays.asList(user));
            List<Privilege> privilegeList = new ArrayList<>();
            for (String privilege : privileges) {
                Privilege privilege1 = new Privilege();
                privilege1.setPermission(privilege);
                privilegeList.add(privilege1);
            }
            roles.setPrivileges(privilegeList);
            roleList.add(roles);
        }

        user.setUserCode(payload.getUserCode());
        user.setFirstName(payload.getFirstname());
        user.setLastName(payload.getLastname());
        user.setEmail(payload.getEmail());
        user.setPassword(passwordEncoder.encode(payload.getPassword()));
        user.setRoles(roleList);
        User savedUser = null;
        try {
            System.out.println("before saving users in db {}, ");
            savedUser = userRepository.save(user);
            System.out.println("users in db {}, " + savedUser);

        } catch (Exception e) {
            e.getMessage();
        }
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserCode(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUserCode(request.getUserCode())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUserId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userCode;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userCode = jwtService.extractUsername(refreshToken);
        if (userCode != null) {
            var user = this.userRepository.findByUserCode(userCode)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
