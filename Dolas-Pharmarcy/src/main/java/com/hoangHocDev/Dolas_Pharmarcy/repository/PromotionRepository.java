package com.hoangHocDev.Dolas_Pharmarcy.repository;

import com.hoangHocDev.Dolas_Pharmarcy.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, String> {

}
