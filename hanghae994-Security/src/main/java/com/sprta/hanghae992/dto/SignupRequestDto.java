package com.sprta.hanghae992.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;



@Getter
public class SignupRequestDto {
    //null, size, pattern순서로 넣어줘야 함
    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9@$!%*#?&]{8,15}$")
    private String password;
//    private boolean admin = false;   //항상 false인데..?
    private String adminToken = "";
}