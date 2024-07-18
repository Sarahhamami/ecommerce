package com.example.ecom.domain;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AuthenticationResponse {
    private String token;
    private Role role;
    private int id;
}
