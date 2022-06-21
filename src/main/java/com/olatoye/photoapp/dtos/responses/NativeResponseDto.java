package com.olatoye.photoapp.dtos.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class NativeResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String cohortName;
    private int cohortNumber;
    private String dateCreated;
}
