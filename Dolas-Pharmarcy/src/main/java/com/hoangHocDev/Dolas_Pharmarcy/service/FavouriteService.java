package com.hoangHocDev.Dolas_Pharmarcy.service;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.FavouriteRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.FavouriteResponse;
import org.springframework.stereotype.Service;

@Service
public interface FavouriteService {
    FavouriteResponse addFavourite(FavouriteRequest request);
    FavouriteResponse removeFavourite(FavouriteRequest request);
    FavouriteResponse getFavouriteList();
}
