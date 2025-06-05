package com.hoangHocDev.Dolas_Pharmarcy.service.Impl;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.ImageRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.ImageResponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Image;
import com.hoangHocDev.Dolas_Pharmarcy.mapper.ImageMapper;
import com.hoangHocDev.Dolas_Pharmarcy.repository.ImageRepository;
import com.hoangHocDev.Dolas_Pharmarcy.service.BaseCRUDService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageServiceImpl implements BaseCRUDService<ImageResponse, ImageRequest> {

    ImageRepository imageRepository;
    ImageMapper imageMapper;

    @Override
    public void delete(String id) {
        imageRepository.deleteById(id);
    }

    @Override
    public ImageResponse create(ImageRequest request) {
        Image image = imageMapper.toImage(request);
        image = imageRepository.save(image);
        return imageMapper.toReponse(image) ;
    }

    @Override
    public List<ImageResponse> findAll() {
        return imageRepository.findAll()
                .stream()
                .map(imageMapper::toReponse)
                .toList();
    }

    @Override
    public Page<ImageResponse> findByPage(int page, int size) {
        return null;
    }

    @Override
    public ImageResponse update(String id, ImageRequest request) {
        return null;
    }

    @Override
    public ImageResponse findByID(String id) {
        return null;
    }
}
