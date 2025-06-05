package com.hoangHocDev.Dolas_Pharmarcy.service;

import org.springframework.data.domain.Page;

import java.util.List;

public interface BaseCRUDService<T, L> {
    void delete(String id);
    T create(L l);
    List<T> findAll();
    Page<T> findByPage(int page,int size);
    T update(String id, L l);
    T findByID(String id);
}
