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
        Event event = eventRepository.findById(reservation.getEventId())
                .orElseThrow(() -> new EventNotFoundException("Event Not Found!"));

        Rule.check(event.getStatus().equals(EventStatus.ACTIVE),
                EventCancelledException::new,
                "Event Must be Active!");

        Rule.check(reservation.getTicketCount() > event.getCapacity() - event.getReservedCount(),
                CapacityExceededException::new,
                "The Number of Tickets Exceeds The Remaining Capacity!");

        Rule.check(reservation.getCustomerName() == null || reservation.getCustomerName().isBlank(),
                InvalidDataException::new,
                "Customer Name Cannot be Null or Empty!");

        Rule.check(reservationRepository.existByPhoneNumber(reservation.getCustomerPhone()),
                InvalidDataException::new,
                "Customer Phone Number Exist!");

        Long id = reservationRepository.save(reservation);

        eventRepository.update(new Event(event.getTitle(), event.getLocation(), event.getCapacity(),
                event.getReservedCount() + reservation.getTicketCount(), event.getTicketPrice(), event.getStatus()));

        return id;
    }

    @Override
    public Reservation change(Reservation reservation) {
        Event event = eventRepository.findById(reservation.getEventId())
                .orElseThrow(() -> new EventNotFoundException("Event Not Found!"));

        Reservation oldReservation = reservationRepository.findById(reservation.getId())
                .orElseThrow(() -> new ReservationNotFoundException("Reservation Not Found!"));

        Rule.check(event.getStatus().equals(EventStatus.ACTIVE),
                EventCancelledException::new,
                "Event Must be Active!");

        Rule.check(reservation.getTicketCount() > event.getCapacity() - event.getReservedCount(),
                CapacityExceededException::new,
                "The Number of Tickets Exceeds The Remaining Capacity!");

        Rule.check(reservation.getCustomerName() == null || reservation.getCustomerName().isBlank(),
                InvalidDataException::new,
                "Customer Name Cannot be Null or Empty!");

        Rule.check(reservationRepository.existByPhoneNumber(reservation.getCustomerPhone()),
                InvalidDataException::new,
                "Customer Phone Number Exist!");

        reservationRepository.update(reservation);

        eventRepository.update(new Event(event.getTitle(), event.getLocation(), event.getCapacity(),
                event.getReservedCount() + (reservation.getTicketCount() - oldReservation.getTicketCount())
                , event.getTicketPrice(), event.getStatus()));

        return reservationRepository.findById(reservation.getId())
                .orElseThrow(() -> new ReservationNotFoundException("Reservation Not Found!"));
    }

    @Override
    public Long cancel(Long id) {
        Rule.check(id < 0,
                InvalidDataException::new,
                "ID Cannot be Negative!");

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation Not Found!"));
        Event event = eventRepository.findById(reservation.getEventId())
                .orElseThrow(() -> new EventNotFoundException("Event Not Found!"));

        reservationRepository.cancel(id);

        eventRepository.update(new Event(event.getTitle(), event.getLocation(), event.getCapacity(),
                event.getReservedCount() - reservation.getTicketCount(), event.getTicketPrice(), event.getStatus()));

        return id;
    }

    @Override
    public Reservation getById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation Not Found!"));
    }

    @Override
    public List<Reservation> getAll() {
        return List.of();
    }
}
