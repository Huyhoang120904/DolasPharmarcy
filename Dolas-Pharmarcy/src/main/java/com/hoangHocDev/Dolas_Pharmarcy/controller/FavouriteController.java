package com.hoangHocDev.Dolas_Pharmarcy.controller;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.FavouriteRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.ApiResponse;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.FavouriteResponse;
import com.hoangHocDev.Dolas_Pharmarcy.service.FavouriteService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favourite")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FavouriteController {
    FavouriteService favouriteService;

    @GetMapping
    public ApiResponse<FavouriteResponse> getFavourite(){
        return ApiResponse.<FavouriteResponse>builder()
                .result(favouriteService.findFavouriteList())
                .build();
    }

    @PostMapping
    public ApiResponse<FavouriteResponse> addFavourite(@RequestBody FavouriteRequest request){
        return ApiResponse.<FavouriteResponse>builder()
                .result(favouriteService.addFavourite(request))
                .build();
    }

    @DeleteMapping
    public ApiResponse<FavouriteResponse> deleteFavourite(@RequestBody FavouriteRequest request){
        return ApiResponse.<FavouriteResponse>builder()
                .result(favouriteService.removeFavourite(request))
                .build();
    }

}
