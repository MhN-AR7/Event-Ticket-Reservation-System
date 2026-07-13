package ir.maktabsharif.event.model;

import ir.maktabsharif.event.enums.ReservationStatus;

import java.time.LocalDate;

public class Reservation extends BaseModel {
    private String customerName;
    private String customerPhone;
    private Long eventId;
    private Integer ticketCount;
    private LocalDate reservationDate;
    private ReservationStatus status;

    public Reservation(Long id, String customerName, String customerPhone, Long eventId, Integer ticketCount, LocalDate reservationDate, ReservationStatus status) {
        super(id);
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.eventId = eventId;
        this.ticketCount = ticketCount;
        this.reservationDate = reservationDate;
        this.status = status;
    }

    public Reservation(String customerName, String customerPhone, Long eventId, Integer ticketCount, LocalDate reservationDate, ReservationStatus status) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.eventId = eventId;
        this.ticketCount = ticketCount;
        this.reservationDate = reservationDate;
        this.status = status;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Integer getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(Integer ticketCount) {
        this.ticketCount = ticketCount;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("""
                Reservation ID: %d | Event ID: %d | Ticket Count: %d
                Customer Name: %s | Customer Phone: %s
                Reservation Date: %s | Status: %s
                """, this.getId(), eventId, ticketCount, customerName, customerPhone, reservationDate, status.toString());
    }
}
