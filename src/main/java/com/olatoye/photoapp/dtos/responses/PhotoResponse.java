package com.olatoye.photoapp.dtos.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Builder
@Setter
@Getter
public class PhotoResponse {
    private Long imageId;
    private Long imageUploaderId;
    private String imageUrl;
    private int numberOfDownloads;
    private Date dateUploaded;
}
