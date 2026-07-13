package ir.maktabsharif.event.service;

import java.util.List;

public interface GenericService<T> {
    Long register(T t);
    T change(T t);
    Long cancel(Long id);
    T getById(Long id);
    List<T> getAll();
}
