package com.example.springmasterv01.models;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthModel {

    private Long id;
    @NonNull
    private String username;
    @NonNull
    private String password;
}
