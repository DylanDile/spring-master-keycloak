package com.example.springmasterv01.core;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {

    private boolean status;
    private String message;
    private Object data;

    public Object build() {
        return this;
    }
}
