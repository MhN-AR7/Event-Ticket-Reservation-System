package ir.maktabsharif.event.repository.reservation;

import ir.maktabsharif.event.model.Reservation;
import ir.maktabsharif.event.repository.GenericRepository;

import java.util.List;

public interface ReservationRepository extends GenericRepository<Reservation> {
    boolean existByPhoneNumber(String phoneNumber);
    List<Reservation> findActiveReservation();
}