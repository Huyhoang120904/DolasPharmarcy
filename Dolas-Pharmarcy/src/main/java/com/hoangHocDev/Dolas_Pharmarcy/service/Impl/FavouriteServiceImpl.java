package com.hoangHocDev.Dolas_Pharmarcy.service.Impl;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.FavouriteRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.FavouriteResponse;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Favourites;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Product;
import com.hoangHocDev.Dolas_Pharmarcy.exception.AppException;
import com.hoangHocDev.Dolas_Pharmarcy.exception.ErrorCode;
import com.hoangHocDev.Dolas_Pharmarcy.mapper.FavouritesMapper;
import com.hoangHocDev.Dolas_Pharmarcy.repository.FavouritesRepository;
import com.hoangHocDev.Dolas_Pharmarcy.repository.ProductRepository;
import com.hoangHocDev.Dolas_Pharmarcy.repository.UserDetailRepository;
import com.hoangHocDev.Dolas_Pharmarcy.service.FavouriteService;
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
        Favourites favourites = favouritesRepository.getFavouritesByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.DATA_NOT_FOUND));
        return favourites;
    }
}
