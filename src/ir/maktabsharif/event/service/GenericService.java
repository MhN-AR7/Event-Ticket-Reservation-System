package ir.maktabsharif.event.service;

import java.util.List;

public interface GenericService<T> {
    Long register(T t);
    boolean change(T t);
    boolean remove(Long id);
    boolean cancel(Long id);
    T getById(Long id);
    List<T> getAll();
}
