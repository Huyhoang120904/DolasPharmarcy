package com.hoangHocDev.Dolas_Pharmarcy.repository;

import com.hoangHocDev.Dolas_Pharmarcy.entity.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetRepository extends JpaRepository<Target, String> {
}
