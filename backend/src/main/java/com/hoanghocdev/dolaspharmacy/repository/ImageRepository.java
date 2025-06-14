package com.hoanghocdev.dolaspharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoanghocdev.dolaspharmacy.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
}
