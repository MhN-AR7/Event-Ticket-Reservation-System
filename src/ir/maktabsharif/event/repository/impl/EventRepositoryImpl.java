package ir.maktabsharif.event.repository.impl;

import ir.maktabsharif.event.enums.EventStatus;
import ir.maktabsharif.event.enums.ReservationStatus;
import ir.maktabsharif.event.exception.DatabaseRepositoryException;
import ir.maktabsharif.event.model.Event;
import ir.maktabsharif.event.repository.GenericRepository;
import ir.maktabsharif.event.util.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventRepositoryImpl implements GenericRepository<Event> {
    @Override
    public Long save(Event event) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO events (title, location, capacity, reserved_count, ticket_price, status) VALUES (?,?,?,?,?,?) RETURNING id"
             )
        ) {
            ps.setString(1, event.getTitle());
            ps.setString(2, event.getLocation());
            ps.setInt(3, event.getCapacity());
            ps.setInt(4, event.getReservedCount());
            ps.setBigDecimal(5, event.getTicketPrice());
            ps.setString(6, event.getStatus().toString());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getLong("id");
                throw new DatabaseRepositoryException("Event ID Not Returned!");
            }
        }
        catch (SQLException e) {
            throw new DatabaseRepositoryException("Insertion to events Table Failed!" + e.getMessage());
        }
    }

    @Override
    public boolean update(Event event) {
        try (Connection connection = DatabaseConfig.getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE events SET title=?, location=?, capacity=?, reserved_count=?, ticket_price=?, status=? WHERE id = ?"
            )
        ) {
            ps.setString(1, event.getTitle());
            ps.setString(2, event.getLocation());
            ps.setInt(3, event.getCapacity());
            ps.setInt(4, event.getReservedCount());
            ps.setBigDecimal(5, event.getTicketPrice());
            ps.setString(6, event.getStatus().toString());

            ps.setLong(7, event.getId());

            int rowsAffected = ps.executeUpdate();

            return !(rowsAffected == 0);
        }
        catch (SQLException e) {
            throw new DatabaseRepositoryException("Update events Table Failed!" + e.getMessage());
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = DatabaseConfig.getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM events WHERE id = ?"
            )
        ) {
            ps.setLong(1, id);

            int rowsAffected = ps.executeUpdate();

            return !(rowsAffected == 0);
        }
        catch (SQLException e) {
            throw new DatabaseRepositoryException("Delete From events Table Failed!" + e.getMessage());
        }
    }

    @Override
    public boolean cancel(Long id) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "UPDATE events SET status = ? WHERE id = ?"
             )
        ) {
            ps.setString(1, EventStatus.CANCELLED.toString());
            ps.setLong(2, id);

            int rowsAffected = ps.executeUpdate();

            return !(rowsAffected == 0);
        }
        catch (SQLException e) {
            throw new DatabaseRepositoryException("Canceling in events Table Failed!" + e.getMessage());
        }
    }

    @Override
    public Optional<Event> findById(Long id) {
        try (Connection connection = DatabaseConfig.getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM events WHERE id = ?"
            )
        ) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(
                            new Event(
                                    rs.getLong(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getInt(4),
                                    rs.getInt(5),
                                    rs.getBigDecimal(6),
                                    EventStatus.valueOf(rs.getString(7))
                            )
                    );
                }

                return Optional.empty();
            }
        }
        catch (SQLException e) {
            throw new DatabaseRepositoryException("Finding By ID From events Table Failed!" + e.getMessage());
        }
    }

    @Override
    public List<Event> findAll() {
        try (Connection connection = DatabaseConfig.getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM events"
            );
            ResultSet rs = ps.executeQuery()
        ) {
            List<Event> events = new ArrayList<>();
            while (rs.next()) {
                events.add(
                        new Event(
                                rs.getLong(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getInt(4),
                                rs.getInt(5),
                                rs.getBigDecimal(6),
                                EventStatus.valueOf(rs.getString(7))
                        )
                );
            }

            return events;
        }
        catch (SQLException e) {
            throw new DatabaseRepositoryException("Finding All From events Table Failed!" + e.getMessage());
        }
    }
}
