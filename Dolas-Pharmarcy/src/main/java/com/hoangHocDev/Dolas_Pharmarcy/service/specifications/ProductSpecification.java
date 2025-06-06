package com.hoangHocDev.Dolas_Pharmarcy.service.specifications;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.ProductSearchRequest;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Product;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Supplier;
import com.hoangHocDev.Dolas_Pharmarcy.entity.Variant;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> getSpecification(ProductSearchRequest request) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            // Simple fields
            if (request.getProductName() != null && !request.getProductName().isEmpty()) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("productName")),
                                "%" + request.getProductName().toLowerCase() + "%"));
            }
            if (request.getSku() != null && !request.getSku().isEmpty()) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("sku")),
                                "%" + request.getSku().toLowerCase() + "%"));
            }
            if (request.getOrigin() != null && !request.getOrigin().isEmpty()) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("origin")),
                                "%" + request.getOrigin().toLowerCase() + "%"));
            }
            if (request.getWarning() != null && !request.getWarning().isEmpty()) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("warning")),
                                "%" + request.getWarning().toLowerCase() + "%"));
            }
            if (request.getIngredients() != null && !request.getIngredients().isEmpty()) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("ingredients")),
                                "%" + request.getIngredients().toLowerCase() + "%"));
            }
            if (request.getDosage() != null && !request.getDosage().isEmpty()) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("dosage")),
                                "%" + request.getDosage().toLowerCase() + "%"));
            }
            if (request.getDescription() != null && !request.getDescription().isEmpty()) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                                "%" + request.getDescription().toLowerCase() + "%"));
            }
            if (request.getUsageInstruction() != null && !request.getUsageInstruction().isEmpty()) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("usageInstruction")),
                                "%" + request.getUsageInstruction().toLowerCase() + "%"));
            }
            if (request.getSlug() != null && !request.getSlug().isEmpty()) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("slug")),
                                "%" + request.getSlug().toLowerCase() + "%"));
            }

            // requiresPrescription
            if (request.getRequiresPrescription() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("requiresPrescription"), request.getRequiresPrescription()));
            }

            // productStatus
            if (request.getProductStatus() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("productStatus"), request.getProductStatus()));
            }

            // Category name
            if (request.getCategoryName() != null && !request.getCategoryName().isEmpty()) {
                Join<Object, Object> categoryJoin = root.join("category", JoinType.LEFT);
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(criteriaBuilder.lower(categoryJoin.get("categoryName")),
                                "%" + request.getCategoryName().toLowerCase() + "%"));
            }

            // Supplier name
            if (request.getSupplierName() != null && !request.getSupplierName().isEmpty()) {
                Join<Product, Supplier> supplierJoin = root.join("supplier", JoinType.LEFT);
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(criteriaBuilder.lower(supplierJoin.get("supplierName")),
                                "%" + request.getSupplierName().toLowerCase() + "%"));
            }

            // Price range (assumes 'variant' is a relation, adjust if needed)
            if (request.getPriceFrom() != null || request.getPriceTo() != null) {
                Join<Product, Variant> variantJoin = root.join("variants", JoinType.LEFT);
                if (request.getPriceFrom() != null) {
                    predicate = criteriaBuilder.and(predicate,
                            criteriaBuilder.greaterThanOrEqualTo(variantJoin.get("price"), request.getPriceFrom()));
                }
                if (request.getPriceTo() != null) {
                    predicate = criteriaBuilder.and(predicate,
                            criteriaBuilder.lessThanOrEqualTo(variantJoin.get("price"), request.getPriceTo()));
                }
            }

            // Discount amount range
            if (request.getDiscountAmountFrom() != null || request.getDiscountAmountTo() != null) {
                Join<Object, Object> promotionJoin = root.join("promotion", JoinType.LEFT);
                if (request.getDiscountAmountFrom() != null) {
                    predicate = criteriaBuilder.and(predicate,
                            criteriaBuilder.greaterThanOrEqualTo(promotionJoin.get("discountAmount"), request.getDiscountAmountFrom()));
                }
                if (request.getDiscountAmountTo() != null) {
                    predicate = criteriaBuilder.and(predicate,
                            criteriaBuilder.lessThanOrEqualTo(promotionJoin.get("discountAmount"), request.getDiscountAmountTo()));
                }
            }

            return predicate;
        };
    }
}