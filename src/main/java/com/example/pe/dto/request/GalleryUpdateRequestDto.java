package com.example.pe.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GalleryUpdateRequestDto {
    private Long representativePhotoId;
    private String galleryTheme;
}
