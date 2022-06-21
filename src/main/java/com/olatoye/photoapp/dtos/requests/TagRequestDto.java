package com.olatoye.photoapp.dtos.requests;

import com.olatoye.photoapp.data.models.Photo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Builder
@Setter
@Getter
public class TagRequestDto {
    @NotNull
    private Long id;

    @NotNull
    private String tagName;

    @NotNull
    private Photo photo;
}
