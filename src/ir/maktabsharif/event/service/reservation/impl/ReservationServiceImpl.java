package ir.maktabsharif.event.service.reservation.impl;

import ir.maktabsharif.event.enums.EventStatus;
import ir.maktabsharif.event.exception.*;
import ir.maktabsharif.event.model.Event;
import ir.maktabsharif.event.model.Reservation;
import ir.maktabsharif.event.repository.event.EventRepository;
import ir.maktabsharif.event.repository.event.impl.EventRepositoryImpl;
import ir.maktabsharif.event.repository.reservation.ReservationRepository;
import ir.maktabsharif.event.repository.reservation.impl.ReservationRepositoryImpl;
import ir.maktabsharif.event.service.reservation.ReservationService;

import java.util.List;

public class ReservationServiceImpl implements ReservationService {
    EventRepository eventRepository = new EventRepositoryImpl();
    ReservationRepository reservationRepository = new ReservationRepositoryImpl();

    @Override
    public Long register(Reservation reservation) {
        Event event = eventRepository.findById(
                reservation.getEventId()).orElseThrow(() -> new EventNotFoundException("Event Not Found!"));

        Rule.check(event.getStatus().equals(EventStatus.ACTIVE),
                EventCancelledException::new,
                "Event Must be Active!");

        Rule.check(reservation.getTicketCount() > event.getCapacity()-event.getReservedCount(),
                CapacityExceededException::new,
                "The Number of Tickets Exceeds The Remaining Capacity!");

        Rule.check(reservation.getCustomerName() == null || reservation.getCustomerName().isBlank(),
                InvalidDataException::new,
                "Customer Name Cannot be Null or Empty!");

        Rule.check(reservationRepository.existByPhoneNumber(reservation.getCustomerPhone()),
                InvalidDataException::new,
                "Customer Phone Number Exist!");

        eventRepository.update(new Event(event.getTitle(), event.getLocation(), event.getCapacity(),
                event.getReservedCount()+reservation.getTicketCount(), event.getTicketPrice(), event.getStatus()));

        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation change(Reservation reservation) {
        Event event = eventRepository.findById(
                reservation.getEventId()).orElseThrow(() -> new EventNotFoundException("Event Not Found!"));

        Rule.check(event.getStatus().equals(EventStatus.ACTIVE),
                EventCancelledException::new,
                "Event Must be Active!");

        Rule.check(reservation.getTicketCount() > event.getCapacity()-event.getReservedCount(),
                CapacityExceededException::new,
                "The Number of Tickets Exceeds The Remaining Capacity!");

        Rule.check(reservation.getCustomerName() == null || reservation.getCustomerName().isBlank(),
                InvalidDataException::new,
                "Customer Name Cannot be Null or Empty!");

        Rule.check(reservationRepository.existByPhoneNumber(reservation.getCustomerPhone()),
                InvalidDataException::new,
                "Customer Phone Number Exist!");

        eventRepository.update(new Event(event.getTitle(), event.getLocation(), event.getCapacity(),
                event.getReservedCount()+reservation.getTicketCount(), event.getTicketPrice(), event.getStatus()));

        return reservationRepository.findById(reservation.getId()).orElseThrow(() -> new ReservationNotFoundException("Reservation Not Found!"));
    }

    @Override
    public Long remove(Long id) {
        return 0L;
    }

    @Override
    public Long cancel(Long id) {
        return 0L;
    }

    @Override
    public Reservation getById(Long id) {
        return null;
    }

    @Override
    public List<Reservation> getAll() {
        return List.of();
    }
}
