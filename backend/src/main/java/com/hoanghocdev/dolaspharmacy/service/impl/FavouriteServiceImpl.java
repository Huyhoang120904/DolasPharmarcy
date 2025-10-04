package com.hoanghocdev.dolaspharmacy.service.impl;

import com.hoanghocdev.dolaspharmacy.dto.response.FavouriteResponse;
import com.hoanghocdev.dolaspharmacy.entity.Favourites;
import com.hoanghocdev.dolaspharmacy.entity.Product;
import com.hoanghocdev.dolaspharmacy.exception.AppException;
import com.hoanghocdev.dolaspharmacy.exception.ErrorCode;
import com.hoanghocdev.dolaspharmacy.mapper.FavouritesMapper;
import com.hoanghocdev.dolaspharmacy.repository.FavouritesRepository;
import com.hoanghocdev.dolaspharmacy.repository.ProductRepository;
import com.hoanghocdev.dolaspharmacy.service.FavouriteService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class FavouriteServiceImpl implements FavouriteService {
   ProductRepository productRepository;
   FavouritesRepository favouritesRepository;
   FavouritesMapper favouritesMapper;

    @Override
    public FavouriteResponse toggleFavourite(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        Favourites favourites = getFavourite();

        if(favourites.getProducts().contains(product)) {
            favourites = removeFavourite(product);
        } else {
            favourites = addFavourite(product);
        }

        return favouritesMapper.toResponse(favourites);
    }

    @Override
    public FavouriteResponse findFavouriteList() {
        return favouritesMapper.toResponse(getFavourite());
    }

    public Favourites addFavourite(Product product) {
        Favourites favourites = getFavourite();
        favourites.getProducts().add(product);
        return favouritesRepository.save(favourites);
    }


    public Favourites removeFavourite(Product product) {
        Favourites favourites = getFavourite();
        favourites.getProducts().removeIf(item ->
                item.getId().equals(product.getId()));
        return favouritesRepository.save(favourites);
    }


    public Favourites getFavourite(){
        SecurityContext context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        return favouritesRepository.getFavouritesByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
    }
}
