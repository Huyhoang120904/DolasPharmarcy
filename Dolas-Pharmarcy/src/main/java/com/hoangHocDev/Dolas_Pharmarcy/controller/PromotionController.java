package com.hoangHocDev.Dolas_Pharmarcy.controller;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.PromotionRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.ApiResponse;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.PromotionResponse;
import com.hoangHocDev.Dolas_Pharmarcy.service.PromotionService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/promotions")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class PromotionController {
    PromotionService promotionService;

    @GetMapping
    public ApiResponse<Page<PromotionResponse>> getPromotionByPage(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<Page<PromotionResponse>>builder()
                .result(promotionService.findByPage(page,size))
                .build();
    }

    @GetMapping("/{promotionId}")
    public  ApiResponse<PromotionResponse> getPromotionById(@PathVariable String promotionId) {
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
    public ApiResponse<PromotionResponse> updatePromotion(@PathVariable String promotionId,@RequestBody PromotionRequest request) {
        return ApiResponse.<PromotionResponse>builder()
                .result(promotionService.update(promotionId,request))
                .build();
    }

    @DeleteMapping("/{promotionId}")
    public ApiResponse deletePromotion(@PathVariable String promotionId) {
        promotionService.delete(promotionId);
        return ApiResponse.<PromotionResponse>builder()
                .build();
    }
}
