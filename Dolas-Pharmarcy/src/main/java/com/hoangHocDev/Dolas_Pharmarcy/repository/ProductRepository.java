package com.hoangHocDev.Dolas_Pharmarcy.repository;

import com.hoangHocDev.Dolas_Pharmarcy.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.lang.ScopedValue;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findBySlug(String slug);
}
