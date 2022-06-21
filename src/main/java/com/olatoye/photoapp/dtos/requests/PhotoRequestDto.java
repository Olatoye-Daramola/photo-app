package com.olatoye.photoapp.dtos.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;


@Builder
@Setter
@Getter
public class PhotoRequestDto {
    @NotNull
    private Long imageUploaderId;

    @NotNull
    private MultipartFile image;

    @NotNull
    private String caption;
}
