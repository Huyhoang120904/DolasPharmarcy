package com.hoanghocdev.dolaspharmacy.repository;

import com.hoanghocdev.dolaspharmacy.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailRepository  extends JpaRepository<UserDetail, String> {

    @Query("select ud from UserDetail ud join UserEntity ue on ue.userDetail.id = ud.id where ue.username = :username")
    Optional<UserDetail> findByUsername(@Param("username") String username);
}
