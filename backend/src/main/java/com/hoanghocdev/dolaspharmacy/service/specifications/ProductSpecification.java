package com.hoanghocdev.dolaspharmacy.service.specifications;

import com.hoanghocdev.dolaspharmacy.dto.request.ProductSearchRequest;
import com.hoanghocdev.dolaspharmacy.entity.*;
import jakarta.persistence.criteria.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductSpecification {

    public static Specification<Product> getSpecification(ProductSearchRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            addSimpleStringPredicates(request, root, criteriaBuilder, predicates);
            addBooleanAndEnumPredicates(request, root, criteriaBuilder, predicates);
            addInListPredicates(request, root, criteriaBuilder, predicates);
            addPricePredicates(request, root, criteriaBuilder, predicates);
            addDiscountPredicates(request, root, criteriaBuilder, predicates);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void addSimpleStringPredicates(ProductSearchRequest request,
                                                  jakarta.persistence.criteria.Root<Product> root,
                                                  jakarta.persistence.criteria.CriteriaBuilder cb,
                                                  List<Predicate> predicates) {
        addLikePredicate(request.getProductName(), "productName", root, cb, predicates);
        addLikePredicate(request.getSku(), "sku", root, cb, predicates);
        addLikePredicate(request.getWarning(), "warning", root, cb, predicates);
        addLikePredicate(request.getIngredients(), "ingredients", root, cb, predicates);
        addLikePredicate(request.getDosage(), "dosage", root, cb, predicates);
        addLikePredicate(request.getDescription(), "description", root, cb, predicates);
        addLikePredicate(request.getUsageInstruction(), "usageInstruction", root, cb, predicates);
        addLikePredicate(request.getSlug(), "slug", root, cb, predicates);
    }

    private static void addInListPredicates(ProductSearchRequest request,
                                                  jakarta.persistence.criteria.Root<Product> root,
                                                  jakarta.persistence.criteria.CriteriaBuilder cb,
                                                  List<Predicate> predicates) {
        addCategoryPredicate(request, root, cb, predicates);
        addSupplierPredicate(request, root, cb, predicates);
        addBrandPredicate(request, root, cb, predicates);
        addTargetPredicate(request, root, cb, predicates);
        addOriginPredicate(request, root, cb, predicates);
    }




    private static void addLikePredicate(String value, String field,
                                         jakarta.persistence.criteria.Root<Product> root,
                                         jakarta.persistence.criteria.CriteriaBuilder cb,
                                         List<Predicate> predicates) {
        if (value != null && !value.isEmpty()) {
            log.info("DB Value: {}", cb.lower(root.get(field)));
            log.info("Request value: {}", "%" + value.toLowerCase() + "%");

            predicates.add(cb.like(cb.lower(root.get(field)), "%" + value.toLowerCase() + "%"));
        }
    }

    private static void addBooleanAndEnumPredicates(ProductSearchRequest request,
                                                    jakarta.persistence.criteria.Root<Product> root,
                                                    jakarta.persistence.criteria.CriteriaBuilder cb,
                                                    List<Predicate> predicates) {
        if (request.getRequiresPrescription() != null) {
            predicates.add(cb.equal(root.get("requiresPrescription"), request.getRequiresPrescription()));
        }
        if (request.getProductStatus() != null) {
            predicates.add(cb.equal(root.get("productStatus"), request.getProductStatus()));
        }
    }

    private static void addCategoryPredicate(ProductSearchRequest request,
                                             jakarta.persistence.criteria.Root<Product> root,
                                             jakarta.persistence.criteria.CriteriaBuilder cb,
                                             List<Predicate> predicates) {
        if (request.getCategoryName() != null && !request.getCategoryName().isEmpty()) {
            Join<Object, Object> categoryJoin = root.join("category", JoinType.LEFT);
            predicates.add(cb.like(cb.lower(categoryJoin.get("categoryName")), "%" + request.getCategoryName().toLowerCase() + "%"));
        }
    }

    private static void addOriginPredicate(ProductSearchRequest request, Root<Product> root, CriteriaBuilder cb, List<Predicate> predicates) {
        if (request.getSupplierName() != null && !request.getSupplierName().isEmpty()) {
            predicates.add(root.get(Product_.origin).in(request.getOrigin()));
        }
    }

    private static void addSupplierPredicate(ProductSearchRequest request,
                                             jakarta.persistence.criteria.Root<Product> root,
                                             jakarta.persistence.criteria.CriteriaBuilder cb,
                                             List<Predicate> predicates) {
        if (request.getSupplierName() != null && !request.getSupplierName().isEmpty()) {
            Join<Product, Supplier> supplierJoin = root.join("supplier", JoinType.LEFT);
            predicates.add(supplierJoin.get(Supplier_.supplierName).in(request.getSupplierName()));
        }
    }

    private static void addBrandPredicate(ProductSearchRequest request,
                                             jakarta.persistence.criteria.Root<Product> root,
                                             jakarta.persistence.criteria.CriteriaBuilder cb,
                                             List<Predicate> predicates) {
        if (request.getBrandName() != null && !request.getBrandName().isEmpty()) {
            Join<Product, Brand> brandJoin = root.join("brand", JoinType.LEFT);
            predicates.add(brandJoin.get(Brand_.brandName).in(request.getBrandName()));
        }
    }

    private static void addTargetPredicate(ProductSearchRequest request,
                                          jakarta.persistence.criteria.Root<Product> root,
                                          jakarta.persistence.criteria.CriteriaBuilder cb,
                                          List<Predicate> predicates) {
        if (request.getTargetName() != null && !request.getTargetName().isEmpty()) {
            Join<Product, Target> targetJoin = root.join("target", JoinType.LEFT);
            predicates.add(targetJoin.get(Target_.targetName).in(request.getTargetName()));
        }
    }

    private static void addPricePredicates(ProductSearchRequest request,
                                           jakarta.persistence.criteria.Root<Product> root,
                                           jakarta.persistence.criteria.CriteriaBuilder cb,
                                           List<Predicate> predicates) {
        if (request.getPriceFrom() != null || request.getPriceTo() != null) {
            Join<Product, Variant> variantJoin = root.join("variants", JoinType.LEFT);
            if (request.getPriceFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(variantJoin.get("price"), request.getPriceFrom()));
            }
            if (request.getPriceTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(variantJoin.get("price"), request.getPriceTo()));
            }
            predicates.add(cb.isTrue(variantJoin.get("isPrimary")));
        }
    }

    private static void addDiscountPredicates(ProductSearchRequest request,
                                              jakarta.persistence.criteria.Root<Product> root,
                                              jakarta.persistence.criteria.CriteriaBuilder cb,
                                              List<Predicate> predicates) {
        if (request.getDiscountAmountFrom() != null || request.getDiscountAmountTo() != null) {
            Join<Object, Object> promotionJoin = root.join("promotion", JoinType.LEFT);
            if (request.getDiscountAmountFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(promotionJoin.get("discountAmount"), request.getDiscountAmountFrom()));
            }
            if (request.getDiscountAmountTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(promotionJoin.get("discountAmount"), request.getDiscountAmountTo()));
            }
        }
    }
}