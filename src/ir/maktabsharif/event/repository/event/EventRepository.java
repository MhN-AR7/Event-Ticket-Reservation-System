package ir.maktabsharif.event.repository.event;

import ir.maktabsharif.event.model.Event;
import ir.maktabsharif.event.repository.GenericRepository;

public interface EventRepository extends GenericRepository<Event> {
    void updateReservedCount(Long id, Integer reservedCount);
}