package com.hoanghocdev.dolaspharmacy.controller;

import com.hoanghocdev.dolaspharmacy.dto.request.TargetRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.ApiResponse;
import com.hoanghocdev.dolaspharmacy.dto.response.TargetResponse;
import com.hoanghocdev.dolaspharmacy.service.TargetService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/targets")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Target")
public class TargetController {
    TargetService targetService;

    @PostMapping
    public ApiResponse<TargetResponse> create(@Valid @RequestBody TargetRequest request) {
        return ApiResponse.<TargetResponse>builder()
                .result(targetService.create(request))
                .build();
    }

    @GetMapping("/page")
    public ApiResponse<Page<TargetResponse>> findByPage(@PageableDefault(page = 0, size = 16,
                                                                sort = "targetName",
                                                                direction = Sort.Direction.ASC)
                                                                Pageable pageable) {
        return ApiResponse.<Page<TargetResponse>>builder()
                .result(targetService.findByPage(pageable))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<TargetResponse> findById(@PathVariable String id) {
        return ApiResponse.<TargetResponse>builder()
                .result(targetService.findByID(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<TargetResponse> update(@PathVariable String id, @Valid @RequestBody TargetRequest request) {
        return ApiResponse.<TargetResponse>builder()
                .result(targetService.update(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id) {
        targetService.delete(id);
        return ApiResponse.<Void>builder().build();
    }
}
