package com.example.ecom.service;

import com.example.ecom.CustomException.ResourceNotFoundException;
import com.example.ecom.config.JwtService;
import com.example.ecom.domain.*;
import com.example.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;


import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository ;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;



    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPw()));
        var optionalUser= repository.findByUsername(request.getUsername());
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var jwtToken= jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .id(optionalUser.get().getId())
                .role(optionalUser.get().getRole())
                .build();
    }

    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername()).role(request.getRole()).name(request.getName()).username(request.getUsername()).pw(passwordEncoder.encode(request.getPw())).build();

        repository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public void updateUser(int id, UpdateRequest request) {
        try {
            Optional<User> existingUserOptional = repository.findById(id);
            if (existingUserOptional.isPresent()) {
                User existingUser = existingUserOptional.get();
                logger.info("Found user with ID {}", id);

                existingUser.setUsername(request.getUsername());
                existingUser.setRole(Role.valueOf(request.getRole()));
                existingUser.setName(request.getName());
                if (request.getPw() != null && !request.getPw().isEmpty()) {
                    existingUser.setPw(passwordEncoder.encode(request.getPw()));
                }

                repository.save(existingUser);
                logger.info("User with ID {} updated successfully", id);
            } else {
                logger.error("User with ID {} not found", id);
                throw new ResourceNotFoundException("User with ID " + id + " not found");
            }
        } catch (Exception e) {
            logger.error("Error updating user with ID {}", id, e);
            throw e;  // rethrow the exception to be handled by the controller or global exception handler
        }
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }
    public void deleteUser(int id) {repository.deleteById(id);}
    public User getUser(int id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }
    public User getUserByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username));
    }
}
