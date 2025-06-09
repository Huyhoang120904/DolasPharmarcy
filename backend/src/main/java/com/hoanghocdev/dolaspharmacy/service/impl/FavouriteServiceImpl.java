package com.hoanghocdev.dolaspharmacy.service.impl;

import com.hoanghocdev.dolaspharmacy.dto.request.FavouriteRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.FavouriteResponse;
import com.hoanghocdev.dolaspharmacy.entity.Favourites;
import com.hoanghocdev.dolaspharmacy.entity.Product;
import com.hoanghocdev.dolaspharmacy.exception.AppException;
import com.hoanghocdev.dolaspharmacy.exception.ErrorCode;
import com.hoanghocdev.dolaspharmacy.mapper.FavouritesMapper;
import com.hoanghocdev.dolaspharmacy.repository.FavouritesRepository;
import com.hoanghocdev.dolaspharmacy.repository.ProductRepository;
import com.hoanghocdev.dolaspharmacy.repository.UserDetailRepository;
import com.hoanghocdev.dolaspharmacy.service.FavouriteService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class FavouriteServiceImpl implements FavouriteService {


   ProductRepository productRepository;
    private final UserDetailRepository userDetailRepository;
    private final FavouritesRepository favouritesRepository;
    private final FavouritesMapper favouritesMapper;

    @Override
    public FavouriteResponse addFavourite(FavouriteRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        Favourites favourites = getFavourite();
        favourites.getProducts().add(product);
        favourites = favouritesRepository.save(favourites);
        return favouritesMapper.toFavouriteResponse(favourites);
    }

    @Override
    public FavouriteResponse removeFavourite(FavouriteRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        Favourites favourites = getFavourite();
        favourites.getProducts().removeIf(item ->
                item.getId().equals(product.getId()));
        favourites = favouritesRepository.save(favourites);
        return favouritesMapper.toFavouriteResponse(favourites);
    }

    @Override
    public FavouriteResponse findFavouriteList() {
        return favouritesMapper.toFavouriteResponse(getFavourite());
    }

    public Favourites getFavourite(){
        SecurityContext context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        return favouritesRepository.getFavouritesByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
    }
}
