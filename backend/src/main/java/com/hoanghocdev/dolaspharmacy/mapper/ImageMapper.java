package com.hoanghocdev.dolaspharmacy.mapper;

import com.hoanghocdev.dolaspharmacy.dto.request.ImageRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.ImageResponse;
import com.hoanghocdev.dolaspharmacy.entity.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    Image toImage(ImageRequest request);
    ImageResponse toResponse(Image image);
}
