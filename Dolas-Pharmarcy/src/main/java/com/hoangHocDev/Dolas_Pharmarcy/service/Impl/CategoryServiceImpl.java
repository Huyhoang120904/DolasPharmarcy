package com.hoangHocDev.Dolas_Pharmarcy.service.Impl;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.CategoryRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.CategoryResponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Category;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Image;
import com.hoangHocDev.Dolas_Pharmarcy.exception.AppException;
import com.hoangHocDev.Dolas_Pharmarcy.exception.ErrorCode;
import com.hoangHocDev.Dolas_Pharmarcy.mapper.CategoryMapper;
import com.hoangHocDev.Dolas_Pharmarcy.mapper.ImageMapper;
import com.hoangHocDev.Dolas_Pharmarcy.repository.CategoryRepository;
import com.hoangHocDev.Dolas_Pharmarcy.repository.ImageRepository;
import com.hoangHocDev.Dolas_Pharmarcy.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public List<CategoryResponse> findAll() {
        return List.of();
    }

    @Override
    public Page<CategoryResponse> findByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
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
