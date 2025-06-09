package com.hoanghocdev.dolaspharmacy.controller;

import com.hoanghocdev.dolaspharmacy.dto.request.FavouriteRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.ApiResponse;
import com.hoanghocdev.dolaspharmacy.dto.response.FavouriteResponse;
import com.hoanghocdev.dolaspharmacy.service.FavouriteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favourite")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Tag(name = "Favourite")
@Validated
public class FavouriteController {
    FavouriteService favouriteService;

    @GetMapping
    public ApiResponse<FavouriteResponse> getFavourite(){
        return ApiResponse.<FavouriteResponse>builder()
                .result(favouriteService.findFavouriteList())
                .build();
    }

    @PostMapping
    public ApiResponse<FavouriteResponse> addFavourite(@RequestBody @Valid FavouriteRequest request){
        return ApiResponse.<FavouriteResponse>builder()
                .result(favouriteService.addFavourite(request))
                .build();
    }

    @DeleteMapping
    public ApiResponse<FavouriteResponse> deleteFavourite(@RequestBody @Valid FavouriteRequest request){
        return ApiResponse.<FavouriteResponse>builder()
                .result(favouriteService.removeFavourite(request))
                .build();
    }

}
