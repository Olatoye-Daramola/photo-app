package com.olatoye.photoapp.dtos.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class TagResponseDto {

    private Long id;
    private String tagName;
}
