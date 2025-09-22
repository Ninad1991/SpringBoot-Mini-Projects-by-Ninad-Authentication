package org.example.userauthservice_begmay2025.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ValidateTokenRequest {

    private String token;
    private Long userId;

}
