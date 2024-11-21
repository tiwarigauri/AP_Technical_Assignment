package com.example.authservice.controller;

import com.example.authservice.model.User;
import com.example.authservice.service.AuthService;
import com.example.authservice.service.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody HashMap<String, String> payload) {
        String email = payload.get("email");
        String password = payload.get("password");
        User user = authService.signUp(email, password);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody HashMap<String, String> payload) {
        String email = payload.get("email");
        String password = payload.get("password");
        Optional<User> user = authService.authenticate(email, password);
        if (user.isPresent()) {
            String token = jwtUtil.generateToken(email);
            return ResponseEntity.ok(new HashMap<>() {{
                put("token", token);
            }});
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(400).body("Authorization token is required");
        }

        token = token.substring(7); // Remove "Bearer "
        String email = jwtUtil.extractEmail(token);

        if (jwtUtil.isTokenValid(token) && !authService.isTokenRevoked(email)) {
            return ResponseEntity.ok("Valid Token");
        }
        return ResponseEntity.status(401).body("Invalid or Expired or Revoked Token");
    }


    @PostMapping("/revoke")
    public ResponseEntity<?> revokeToken(@RequestBody HashMap<String, String> payload) {
        Long userId = Long.parseLong(payload.get("userId"));
        authService.revokeToken(userId);
        return ResponseEntity.ok("Token revoked");
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody HashMap<String, String> payload) {
        try {
            String oldToken = payload.get("token");
            String newToken = authService.refreshToken(oldToken);
            return ResponseEntity.ok(new HashMap<>() {{
                put("newToken", newToken);
            }});
        } catch (IllegalStateException e) {
            return ResponseEntity.status(401).body("Invalid or Expired Token");
        }
    }

}
