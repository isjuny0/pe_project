package com.example.pe.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PhotoUploadRequestDto {
    private String title;
    private String description;
    private String tags;
    private MultipartFile photo;    // S3 업로드 대상
}
