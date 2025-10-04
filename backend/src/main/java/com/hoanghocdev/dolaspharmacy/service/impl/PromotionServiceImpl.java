package com.hoanghocdev.dolaspharmacy.service.impl;

import com.hoanghocdev.dolaspharmacy.dto.request.PromotionRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.PromotionResponse;
import com.hoanghocdev.dolaspharmacy.entity.Promotion;
import com.hoanghocdev.dolaspharmacy.entity.enums.PromotionType;
import com.hoanghocdev.dolaspharmacy.entity.notification.Notification;
import com.hoanghocdev.dolaspharmacy.entity.notification.enums.NotificationType;
import com.hoanghocdev.dolaspharmacy.exception.AppException;
import com.hoanghocdev.dolaspharmacy.exception.ErrorCode;
import com.hoanghocdev.dolaspharmacy.mapper.PromotionMapper;
import com.hoanghocdev.dolaspharmacy.repository.NotificationRepository;
import com.hoanghocdev.dolaspharmacy.repository.PromotionRepository;
import com.hoanghocdev.dolaspharmacy.service.PromotionService;
import com.hoanghocdev.dolaspharmacy.utils.SlugUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PromotionServiceImpl implements PromotionService {
    PromotionRepository promotionRepository;
    PromotionMapper promotionMapper;
    KafkaTemplate<String, Object> kafkaTemplate;
    NotificationRepository notificationRepository;

    @Override
    public void delete(String id) {
        if (!promotionRepository.existsById(id)) {
            throw new AppException(ErrorCode.DATA_NOT_FOUND);
        }
        promotionRepository.deleteById(id);
    }

    @Override
    public PromotionResponse create(PromotionRequest promotionRequest) {
        Promotion promotion = promotionMapper.toPromotion(promotionRequest);
        promotion.setSlug(SlugUtils.toSlug(promotion.getPromotionName()));
        promotion = promotionRepository.save(promotion);

        Notification notification = Notification.builder()
                .message("Chương trình khuyến mãi " + promotion.getPromotionName() + "đã lên sóng. Bạn đừng bỏ lỡ nhé!")
                .url("/promotion/" + promotion.getSlug())
                .startDate(promotion.getStartDate())
                .endDate(promotion.getEndDate())
                .notificationType(NotificationType.PROMOTION)
                .build();

        if (promotion.getImage() != null && StringUtils.hasText(promotion.getImage().getUrl()) ) {
            notification.setImageUrl(promotion.getImage().getUrl());
        }

        notification = notificationRepository.save(notification);

        kafkaTemplate.send("ws-notification", notification);

        return promotionMapper.toPromotionResponse(promotion);
    }

    @Override
    public Page<PromotionResponse> findByPage(Pageable pageable) {
        return promotionRepository.findAll(pageable)
                .map(promotionMapper::toPromotionResponse);
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

    @Override
    public Optional<Promotion> getHighestAvailablePromotionForOrderByPromotionType(PromotionType promotionType) {
        return promotionRepository.findActivePromotionByType(promotionType, LocalDate.now());
    }
}
