package com.hoangHocDev.Dolas_Pharmarcy.repository;

import com.hoangHocDev.Dolas_Pharmarcy.entity.Favourites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavouritesRepository  extends JpaRepository<Favourites, String> {
    @Query("select f from Favourites f join UserDetail ud on f.userDetail.id = ud.id where ud.userEntity.username = :username")
    Optional<Favourites> getFavouritesByUsername(@Param("username") String username);
}
