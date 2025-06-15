package com.hoanghocdev.dolaspharmacy.controller;

import com.hoanghocdev.dolaspharmacy.dto.request.PromotionRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.ApiResponse;
import com.hoanghocdev.dolaspharmacy.dto.response.PromotionResponse;
import com.hoanghocdev.dolaspharmacy.service.PromotionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/promotions")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@Tag(name = "Promotion")
@Validated
public class PromotionController {
    PromotionService promotionService;

    @GetMapping
    public ApiResponse<Page<PromotionResponse>> getPromotionByPage(@PageableDefault(page = 0, size = 16,
                                                                    sort = "productName",
                                                                    direction = Sort.Direction.ASC)
                                                                   Pageable pageable) {
        return ApiResponse.<Page<PromotionResponse>>builder()
                .result(promotionService.findByPage(pageable))
                .build();
    }

    @GetMapping("/{promotionId}")
    public ApiResponse<PromotionResponse> getPromotionById(@PathVariable String promotionId) {
        return ApiResponse.<PromotionResponse>builder()
                .result(promotionService.findByID(promotionId))
                .build();
    }

    @PostMapping
    public ApiResponse<PromotionResponse> createPromotion(@RequestBody PromotionRequest request) {
        return ApiResponse.<PromotionResponse>builder()
                .result(promotionService.create(request))
                .build();
    }

    @PutMapping("/{promotionId}")
    public ApiResponse<PromotionResponse> updatePromotion(@PathVariable String promotionId,
                                                          @RequestBody @Valid PromotionRequest request) {
        return ApiResponse.<PromotionResponse>builder()
                .result(promotionService.update(promotionId, request))
                .build();
    }

    @DeleteMapping("/{promotionId}")
    public ApiResponse deletePromotion(@PathVariable String promotionId) {
        promotionService.delete(promotionId);
        return ApiResponse.<PromotionResponse>builder()
                .build();
    }

}
