package com.example.authservice.service;

import com.example.authservice.model.User;
import com.example.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User signUp(String email, String password) {
        User user = new User(null, email, encoder.encode(password), false);
        return userRepository.save(user);
    }

    public Optional<User> authenticate(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && encoder.matches(password, user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }

    public void revokeToken(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setTokenRevoked(true);
        userRepository.save(user);
    }

    public String refreshToken(String oldToken) {
        if (!jwtUtil.isTokenValid(oldToken)) {
            throw new IllegalStateException("Invalid or Expired Token");
        }
        return jwtUtil.refreshToken(oldToken);
    }
    public boolean isTokenRevoked(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() && user.get().isTokenRevoked();
    }


}
