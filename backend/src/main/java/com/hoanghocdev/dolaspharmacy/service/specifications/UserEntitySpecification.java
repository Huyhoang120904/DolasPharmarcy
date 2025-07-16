package com.hoanghocdev.dolaspharmacy.service.specifications;

import com.hoanghocdev.dolaspharmacy.dto.request.UserEntitySearchRequest;
import com.hoanghocdev.dolaspharmacy.entity.UserDetail;
import com.hoanghocdev.dolaspharmacy.entity.UserEntity;
import jakarta.persistence.criteria.*;
import jdk.jfr.Frequency;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntitySpecification {

    public static Specification<UserEntity> getSpecification(UserEntitySearchRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Join<UserEntity, UserDetail> userDetailJoin = root.join("userDetail", JoinType.LEFT);
            if (request.getSearch() != null) {
                List<Predicate> searchPredicates = new ArrayList<>();
                searchPredicates.add(cb.like( cb.lower(userDetailJoin.get("fullName")),"%" +request.getSearch() + "%"));
                searchPredicates.add(cb.like( cb.lower(userDetailJoin.get("email")),"%" +request.getSearch() + "%"));
                predicates.add(cb.or(searchPredicates.toArray(new Predicate[0])));
            }

            buildOrder(predicates, query, cb, userDetailJoin, request);

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static void buildOrder(List<Predicate> predicates,
                                  CriteriaQuery<?> query,
                                  CriteriaBuilder cb,
                                  Join<UserEntity, UserDetail> userDetailJoin,
                                  UserEntitySearchRequest request){
        List<Order> orders = new ArrayList<>();

        if (request.getSortFieldName() !=null && request.getDirection()!=null) {
            if (("desc").equals(request.getDirection().toLowerCase(Locale.ROOT))) {
                orders.add(cb.desc(userDetailJoin.get(request.getSortFieldName())));
            } else {
                orders.add(cb.asc(userDetailJoin.get(request.getSortFieldName())));
            }
        }
        if (!orders.isEmpty()) {
            query.orderBy(orders);

        }
    }




}
