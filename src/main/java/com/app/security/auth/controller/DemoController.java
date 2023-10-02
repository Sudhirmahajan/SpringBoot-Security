package com.app.security.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAnyRole('ADMIN','user')")
public class DemoController {
    @GetMapping("/api/v1/demo-controller/greet")
    public ResponseEntity<String> greet() {
        return ResponseEntity.ok("Hello from secured endpoint");
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("this is test");
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/roletest")
    public ResponseEntity<String> roleTest() {
        return ResponseEntity.ok("this is role test");
    }
    @PreAuthorize("hasAuthority('ADMIN:read')")
    @GetMapping("/authoritytest")
    public ResponseEntity<String> authorityTest() {
        return ResponseEntity.ok("this is authority test");
    }

    @PreAuthorize("hasPermission('read')")
    @GetMapping("/permissiontest")
    public ResponseEntity<String> permissionTest() {
        return ResponseEntity.ok("this is permission test");
    }
}
