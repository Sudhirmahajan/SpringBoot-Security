package com.app.security.auth.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('user')")
public class AdminController {
    @GetMapping
    //@PreAuthorize("hasAuthority('user:read')")
    public String get() {
        return "GET:: admin controller";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user:create')")
    @Hidden
    public String post() {
        return "POST:: admin controller";
    }

    @PutMapping
    @PreAuthorize("hasAuthority('USER:update')")
    @Hidden
    public String put() {
        return "PUT:: admin controller";
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('user:delete')")
    @Hidden
    public String delete() {
        return "DELETE:: admin controller";
    }
}
