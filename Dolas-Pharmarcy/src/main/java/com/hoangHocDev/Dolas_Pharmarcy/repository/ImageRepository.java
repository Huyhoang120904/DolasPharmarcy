package com.hoangHocDev.Dolas_Pharmarcy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoangHocDev.Dolas_Pharmarcy.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
}
