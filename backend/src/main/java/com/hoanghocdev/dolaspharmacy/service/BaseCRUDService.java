package com.hoanghocdev.dolaspharmacy.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaseCRUDService<T, L> {
    void delete(String id);
    T create(L l);
    Page<T> findByPage(Pageable pageable);
    T update(String id, L l);
    T findByID(String id);
}
