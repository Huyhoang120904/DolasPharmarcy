package com.hoangHocDev.Dolas_Pharmarcy.repository;

import com.hoangHocDev.Dolas_Pharmarcy.entity.Cart;
import com.hoangHocDev.Dolas_Pharmarcy.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    @Query("select c from Cart c join UserDetail ud on c.userDetail.id = ud.id where ud.userEntity.username = :username")
    Optional<Cart> findByUsername(@Param("username") String username);
}
