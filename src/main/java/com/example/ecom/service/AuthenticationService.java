package com.example.ecom.service;

import com.example.ecom.config.JwtService;
import com.example.ecom.domain.AuthenticationRequest;
import com.example.ecom.domain.AuthenticationResponse;
import com.example.ecom.domain.RegisterRequest;
import com.example.ecom.domain.User;
import com.example.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository ;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;




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

}
