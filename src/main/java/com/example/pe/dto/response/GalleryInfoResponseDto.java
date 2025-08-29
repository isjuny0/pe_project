package com.example.pe.dto.response;

import com.example.pe.entity.Photo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GalleryInfoResponseDto {
    private Long userId;
    private String nickname;
    private PhotoResponseDto representativePhoto;
    private String galleryTheme;
    private List<PhotoResponseDto> photos;

}
