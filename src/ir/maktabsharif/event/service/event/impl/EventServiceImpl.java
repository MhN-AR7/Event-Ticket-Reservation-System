package ir.maktabsharif.event.service.event.impl;

import ir.maktabsharif.event.exception.*;
import ir.maktabsharif.event.model.Event;
import ir.maktabsharif.event.repository.event.EventRepository;
import ir.maktabsharif.event.repository.event.impl.EventRepositoryImpl;
import ir.maktabsharif.event.service.event.EventService;

import java.math.BigDecimal;
import java.util.List;

public class EventServiceImpl implements EventService {
    EventRepository eventRepository = new EventRepositoryImpl();

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
    public Event change(Event event) {
        Rule.check(event.getTitle() == null || event.getTitle().isBlank(),
                InvalidDataException::new,
                "Title Cannot be Null or Empty!");

        Rule.check(event.getCapacity() < 0,
                InvalidDataException::new,
                "Capacity Cannot be Negative!");

        Rule.check(event.getTicketPrice().compareTo(BigDecimal.ZERO) < 0,
                InvalidDataException::new,
                "Price Cannot be Negative!");

        Rule.check(eventRepository.update(event),
                EventNotFoundException::new,
                "Event Not Found!");

        return eventRepository.findById(event.getId())
                .orElseThrow(() -> new EventNotFoundException("Event Not Found!"));
    }

    @Override
    public Long cancel(Long id) {
        Rule.check(id < 0,
                InvalidDataException::new,
                "ID Cannot be Negative!");

        if (!eventRepository.cancel(id)) throw new EventNotFoundException("Event Not Found!");

        return id;
    }

    @Override
    public Event getById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event Not Found!"));
    }

    @Override
    public List<Event> getAll() {
        List<Event> events = eventRepository.findAll();

        if (events.isEmpty()) throw new EventNotFoundException("Event List is Empty!");
        return events;
    }

    @Override
    public Integer getActiveEventCount() {
        return eventRepository.findActiveEventCount();
    }

    @Override
    public List<Event> getMostExpensive() {
        List<Event> events = eventRepository.findMostExpensive();

        if (events.isEmpty()) throw new EventNotFoundException("Event List is Empty!");
        return events;
    }

    @Override
    public BigDecimal getAverageTicketPrice() {
        return eventRepository.findAverageTicketPrice();
    }

    @Override
    public List<Event> getFullyBooked() {
        List<Event> events = eventRepository.findFullyBooked();

        if (events.isEmpty()) throw new EventNotFoundException("No Event Fully Booked!");
        return events;
    }
}
