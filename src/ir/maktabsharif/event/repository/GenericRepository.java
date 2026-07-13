package ir.maktabsharif.event.repository;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T> {
    Long save(T t);
    boolean update(T t);
    boolean delete(Long id);
    boolean cancel(Long id);
    Optional<T> findById(Long id);
    List<T> findAll();
}
