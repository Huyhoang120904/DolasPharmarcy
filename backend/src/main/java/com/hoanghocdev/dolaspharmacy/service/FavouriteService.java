package com.hoanghocdev.dolaspharmacy.service;

import com.hoanghocdev.dolaspharmacy.dto.request.FavouriteRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.FavouriteResponse;
import org.springframework.stereotype.Service;

@Service
public interface FavouriteService {
    FavouriteResponse toggleFavourite(String id);
    FavouriteResponse findFavouriteList();
}
