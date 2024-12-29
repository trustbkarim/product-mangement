package com.product.controllers;

import com.product.dao.dto.LoginDto;
import com.product.dao.dto.UserDto;
import com.product.security.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/account")
    public ResponseEntity<String> createAccount(@RequestBody UserDto userDTO) {
        authService.registerUser(userDTO);
        return ResponseEntity.ok("Utilisateur enregistré avec succès !");
    }

    @PostMapping("/token")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
        String token = authService.authenticateUser(loginDto);
        return ResponseEntity.ok(token);
    }
}
