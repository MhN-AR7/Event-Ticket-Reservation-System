package ir.maktabsharif.event.service.event.impl;

import ir.maktabsharif.event.exception.InvalidDataException;
import ir.maktabsharif.event.exception.Rule;
import ir.maktabsharif.event.model.Event;
import ir.maktabsharif.event.repository.event.impl.EventRepositoryImpl;
import ir.maktabsharif.event.service.event.EventService;

import java.math.BigDecimal;
import java.util.List;

public class EventServiceImpl implements EventService {
    EventRepositoryImpl eventRepository = new EventRepositoryImpl();

    @Override
    public Long register(Event event) {
        Rule.check(event.getTitle() == null || event.getTitle().isBlank(),
                InvalidDataException::new,
                "Title Cannot be Null or Empty!");

        Rule.check(event.getCapacity() < 0,
                InvalidDataException::new,
                "Capacity Cannot be Negative!");

        Rule.check(event.getTicketPrice().compareTo(BigDecimal.ZERO) < 0,
                InvalidDataException::new,
                "Price Cannot be Negative!");

        return eventRepository.save(event);
    }

    @Override
    public boolean change(Event event) {
        return false;
    }

    @Override
    public boolean remove(Long id) {
        return false;
    }

    @Override
    public boolean cancel(Long id) {
        return false;
    }

    @Override
    public Event getById(Long id) {
        return null;
    }

    @Override
    public List<Event> getAll() {
        return List.of();
    }
}
