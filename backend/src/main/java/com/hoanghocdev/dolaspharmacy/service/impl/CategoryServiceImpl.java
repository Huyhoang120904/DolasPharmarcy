package com.hoanghocdev.dolaspharmacy.service.impl;

import com.hoanghocdev.dolaspharmacy.dto.request.CategoryRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.CategoryResponse;
import com.hoanghocdev.dolaspharmacy.entity.Category;
import com.hoanghocdev.dolaspharmacy.entity.Image;
import com.hoanghocdev.dolaspharmacy.exception.AppException;
import com.hoanghocdev.dolaspharmacy.exception.ErrorCode;
import com.hoanghocdev.dolaspharmacy.mapper.CategoryMapper;
import com.hoanghocdev.dolaspharmacy.mapper.ImageMapper;
import com.hoanghocdev.dolaspharmacy.repository.CategoryRepository;
import com.hoanghocdev.dolaspharmacy.repository.ImageRepository;
import com.hoanghocdev.dolaspharmacy.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;
    ImageMapper imageMapper;
    ImageRepository imageRepository;

    @Override
    public void delete(String id) {
        if(!categoryRepository.existsById(id)) {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryResponse create(CategoryRequest categoryRequest) {

        Category category = categoryMapper.toCatergory(categoryRequest);

        if (categoryRequest.getParentCategoryId()!=null) {
            Category parentCategory = categoryRepository
                    .findById(categoryRequest.getParentCategoryId())
                    .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
            category.setParentCategory(parentCategory);
        }

        Image image = imageMapper.toImage(categoryRequest.getImage());
        imageRepository.save(image);
        category.setImage(image);

        category = categoryRepository.save(category);
        return categoryMapper.toCatergoryResponse(category);
    }

    @Override
    public Page<CategoryResponse> findByPage(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toCatergoryResponse);
    }

    @Override
    public CategoryResponse update(String id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        categoryMapper.updateCatergory(categoryRequest, category);

        Category parentCategory = categoryRepository
                .findById(categoryRequest.getParentCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        category.setParentCategory(parentCategory);

        Image image = imageMapper.toImage(categoryRequest.getImage());
        category.setImage(image);

        category = categoryRepository.save(category);
        return categoryMapper.toCatergoryResponse(category);
    }

    @Override
    public CategoryResponse findByID(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        return categoryMapper.toCatergoryResponse(category);
    }

    @Override
    public CategoryResponse findBySlug(String slug) {
        Category category = (Category) categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        return categoryMapper.toCatergoryResponse(category);
    }
}
