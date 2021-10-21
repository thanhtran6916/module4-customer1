package com.customer.service;

import java.util.List;
import java.util.Optional;

public interface IGeneralService<T> {

    Iterable<T> getAll();

    void save(T t);

    void delete(Long id);

    Optional<T> getById(Long id) throws Exception;
}
