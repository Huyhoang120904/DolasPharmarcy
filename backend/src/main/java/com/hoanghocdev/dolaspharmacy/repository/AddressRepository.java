package com.hoanghocdev.dolaspharmacy.repository;

import com.hoanghocdev.dolaspharmacy.entity.Address;
import com.hoanghocdev.dolaspharmacy.entity.UserDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
    Page<Address> findByUserDetail(UserDetail userDetail, Pageable pageable);
}
