package ir.maktabsharif.event.repository.event;

import ir.maktabsharif.event.model.Event;
import ir.maktabsharif.event.repository.GenericRepository;

import java.math.BigDecimal;
import java.util.List;

public interface EventRepository extends GenericRepository<Event> {
    Integer findActiveEventCount();
    List<Event> findMostExpensive();
    BigDecimal findAverageTicketPrice();
}