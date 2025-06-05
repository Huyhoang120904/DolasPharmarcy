package com.hoangHocDev.Dolas_Pharmarcy.service.Impl;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.PromotionRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.PromotionResponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Promotion;
import com.hoangHocDev.Dolas_Pharmarcy.exception.AppException;
import com.hoangHocDev.Dolas_Pharmarcy.exception.ErrorCode;
import com.hoangHocDev.Dolas_Pharmarcy.mapper.PromotionMapper;
import com.hoangHocDev.Dolas_Pharmarcy.repository.PromotionRepository;
import com.hoangHocDev.Dolas_Pharmarcy.service.BaseCRUDService;
import com.hoangHocDev.Dolas_Pharmarcy.service.PromotionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PromotionServiceImpl implements PromotionService {
    PromotionRepository promotionRepository;
    PromotionMapper promotionMapper;


    @Override
    public void delete(String id) {
        promotionRepository.deleteById(id);
    }

    @Override
    public PromotionResponse create(PromotionRequest promotionRequest) {
        Promotion promotion = promotionMapper.toPromotion(promotionRequest);
        promotion = promotionRepository.save(promotion);
        return promotionMapper.toPromotionResponse(promotion);
    }

    @Override
    public List<PromotionResponse> findAll() {
        List<Promotion> promotions = promotionRepository.findAll();
        return promotions.stream()
                .map(promotionMapper::toPromotionResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<PromotionResponse> findByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return promotionRepository.findAll(pageable).map(promotionMapper::toPromotionResponse) ;
    }

    @Override
    public PromotionResponse update(String id, PromotionRequest promotionRequest) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));

        promotionMapper.updatePromotion(promotionRequest, promotion);

        promotion = promotionRepository.save(promotion);
        return promotionMapper.toPromotionResponse(promotion);
    }

    @Override
    public PromotionResponse findByID(String id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        return promotionMapper.toPromotionResponse(promotion);
    }
}
