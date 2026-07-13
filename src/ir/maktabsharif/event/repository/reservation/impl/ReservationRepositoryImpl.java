package ir.maktabsharif.event.repository.reservation.impl;

import ir.maktabsharif.event.enums.ReservationStatus;
import ir.maktabsharif.event.exception.DatabaseRepositoryException;
import ir.maktabsharif.event.model.Reservation;
import ir.maktabsharif.event.repository.reservation.ReservationRepository;
import ir.maktabsharif.event.util.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservationRepositoryImpl implements ReservationRepository {
    @Override
    public Long save(Reservation reservation) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO reservations (customer_name, customer_phone, event_id, ticket_count, reservation_date, status) VALUES (?,?,?,?,?,?) RETURNING id"
             )
        ) {
            ps.setString(1, reservation.getCustomerName());
            ps.setString(2, reservation.getCustomerPhone());
            ps.setLong(3, reservation.getEventId());
            ps.setInt(4, reservation.getTicketCount());
            ps.setDate(5, Date.valueOf(reservation.getReservationDate()));
            ps.setString(6, reservation.getStatus().toString());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getLong("id");
                throw new DatabaseRepositoryException("Reservation ID Not Returned!");
            }
        }
        catch (SQLException e) {
            throw new DatabaseRepositoryException("Insertion to reservations Table Failed!" + e.getMessage());
        }
    }

    @Override
    public boolean update(Reservation reservation) {
        try (Connection connection = DatabaseConfig.getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE reservations SET customer_name=?, customer_phone=?, event_id=?, ticket_count=?, reservation_date=?, status=? WHERE id = ?"
            )
        ) {
            ps.setString(1, reservation.getCustomerName());
            ps.setString(2, reservation.getCustomerPhone());
            ps.setLong(3, reservation.getEventId());
            ps.setInt(4, reservation.getTicketCount());
            ps.setDate(5, Date.valueOf(reservation.getReservationDate()));
            ps.setString(6, reservation.getStatus().toString());

            ps.setLong(7, reservation.getId());

            int rowsAffected = ps.executeUpdate();

            return !(rowsAffected == 0);
        }
        catch (SQLException e) {
            throw new DatabaseRepositoryException("Updating in reservation Table Failed!" + e.getMessage());
        }
    }

    @Override
    public boolean cancel(Long id) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "UPDATE reservations SET status = ? WHERE id = ?"
             )
        ) {
            ps.setString(1, ReservationStatus.CANCELLED.toString());
            ps.setLong(2, id);

            int rowsAffected = ps.executeUpdate();

            return !(rowsAffected == 0);
        }
        catch (SQLException e) {
            throw new DatabaseRepositoryException("Canceling in reservations Table Failed!" + e.getMessage());
        }
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        try (Connection connection = DatabaseConfig.getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM reservations WHERE id = ?"
            )
        ) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(
                            new Reservation(
                                    rs.getLong(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getLong(4),
                                    rs.getInt(5),
                                    rs.getDate(6).toLocalDate(),
                                    ReservationStatus.valueOf(rs.getString(7))
                            )
                    );
                }

                return Optional.empty();
            }
        }
        catch (SQLException e) {
            throw new DatabaseRepositoryException("Finding By ID From reservations Table Failed!" + e.getMessage());
        }
    }

    @Override
    public List<Reservation> findAll() {
        try (Connection connection = DatabaseConfig.getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM reservations"
            );
            ResultSet rs = ps.executeQuery()
        ) {
            List<Reservation> reservations = new ArrayList<>();
            while (rs.next()) {
                reservations.add(
                        new Reservation(
                                rs.getLong(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getLong(4),
                                rs.getInt(5),
                                rs.getDate(6).toLocalDate(),
                                ReservationStatus.valueOf(rs.getString(7))
                        )
                );
            }

            return reservations;
        }
        catch (SQLException e) {
            throw new DatabaseRepositoryException("Finding All From reservations Table Failed!" + e.getMessage());
        }
    }

    @Override
    public boolean existByPhoneNumber(String phoneNumber) {
        try (Connection connection = DatabaseConfig.getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM reservations WHERE customer_phone = ?"
            )
        ) {
            ps.setString(1, phoneNumber);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
        catch (SQLException e) {
            throw new DatabaseRepositoryException("Existing Phone Number From reservations Table Failed!" + e.getMessage());
        }
    }
}
