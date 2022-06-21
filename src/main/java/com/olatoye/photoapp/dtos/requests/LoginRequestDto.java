package com.olatoye.photoapp.dtos.requests;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Builder
@Setter
@Getter
public class LoginRequestDto {
    @NotNull(message = "Username field cannot be empty")
    private String userName;

    @NotNull(message = "Email field cannot be null")
    @Email(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+" + "@semicolon.africa", message = "Incorrect password")
    private String email;
}
