package com.app.security.service;

import com.app.security.domain.user.*;
import com.app.security.dto.AuthenticationRequest;
import com.app.security.dto.AuthenticationResponse;
import com.app.security.dto.RegisterRequest;
import com.app.security.repository.users.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
    private final RoleRepository roleRepository;
    private final GroupRepository groupRepository;
    public AuthenticationResponse register(RegisterRequest payload) {
        User user = new User();

        List<Long> roleIds = roleRepository.findByGroupNames(payload.getGroups().stream()
                .map(Group::getName).collect(Collectors.toList()))
                .stream().map(Role::getRoleId).collect(Collectors.toList());
        List<Group> groups = groupRepository.findByNameIn(payload.getGroups().stream()
                .map(Group::getName).collect(Collectors.toList()));
        System.out.println("printing group roles " + roleIds);
        List<Role> roles = roleRepository.findByRoleIdIn(roleIds);
        user.setUserCode(payload.getUserCode());
        user.setFirstName(payload.getFirstname());
        user.setLastName(payload.getLastname());
        user.setEmail(payload.getEmail());
        user.setPassword(passwordEncoder.encode(payload.getPassword()));
        user.setGroups(groups);
       // user.setRoles(roles);
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
        var token = Token
                .builder()
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
