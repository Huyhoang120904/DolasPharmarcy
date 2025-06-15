package com.hoanghocdev.dolaspharmacy.mapper;

import com.hoanghocdev.dolaspharmacy.dto.request.FavouriteRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.FavouriteResponse;
import com.hoanghocdev.dolaspharmacy.entity.Favourites;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
        ProductMapper.class
})
public interface FavouritesMapper {
    Favourites toFavourites(FavouriteRequest request);
    FavouriteResponse toResponse(Favourites favourites);
}
