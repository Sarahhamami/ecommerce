package com.example.ecom.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRequest {
    private String username;
    private String role;
    private String name;
    private String pw; // password

    // getters and setters
}
