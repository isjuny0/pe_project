package com.example.pe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GalleryListItemDto {
    private Long userId;
    private String nickname;
    private PhotoResponseDto representativePhoto;
    private String galleryTheme;
    private Long visitCount;
}
