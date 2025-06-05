package com.hoangHocDev.Dolas_Pharmarcy.mapper;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.ImageRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.ImageResponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    Image toImage(ImageRequest request);
    ImageResponse toReponse(Image image);
}
