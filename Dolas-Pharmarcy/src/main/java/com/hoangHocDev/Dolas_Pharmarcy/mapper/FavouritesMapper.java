package com.hoangHocDev.Dolas_Pharmarcy.mapper;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.FavouriteRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.FavouriteResponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Favourites;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FavouritesMapper {
    Favourites toFavourites(FavouriteRequest request);
    FavouriteResponse toFavouriteResponse(Favourites favourites);
}
