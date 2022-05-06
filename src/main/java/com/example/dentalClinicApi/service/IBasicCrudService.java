package com.example.dentalClinicApi.service;

import java.util.Collection;

public interface IBasicCrudService<T> {
    T create(T t);

    T findOne(Integer id);

    Collection<T> findAll();

    T update(T t, Integer id);

    void delete(Integer id);

}
