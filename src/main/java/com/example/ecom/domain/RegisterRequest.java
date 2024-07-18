package com.example.ecom.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RegisterRequest {
    private String name;
    private String username;
    private String pw;
    private Role role;

}
