package com.hoanghocdev.dolaspharmacy.repository;

import com.hoanghocdev.dolaspharmacy.entity.Promotion;
import com.hoanghocdev.dolaspharmacy.entity.enums.PromotionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, String> {
    @Query(value = "SELECT * FROM Promotion WHERE promotion_type = :type AND start_date <= :date AND end_date >= :date ORDER BY discount_amount DESC LIMIT 1", nativeQuery = true)
    Optional<Promotion> findActivePromotionByType(@Param("type") PromotionType promotionType, @Param("date") LocalDate date);
}
