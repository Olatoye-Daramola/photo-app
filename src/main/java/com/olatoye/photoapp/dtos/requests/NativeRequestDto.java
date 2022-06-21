package com.olatoye.photoapp.dtos.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
public class NativeRequestDto {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private int cohortNumber;

    private String cohortName;
}
