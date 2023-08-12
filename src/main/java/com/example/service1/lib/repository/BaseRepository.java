package com.example.service1.lib.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {
    public List<T> getAll() throws Exception;

    public Optional<T> findById(Long id) throws Exception;

    public T create(T data) throws Exception;

    public T findByIdAndUpdate(Long id, T data) throws Exception;

    public Boolean delete(Long id) throws Exception;

}
