package ir.maktabsharif.event.model;

import ir.maktabsharif.event.enums.EventStatus;

import java.math.BigDecimal;

public class Event extends BaseModel {
    private String title;
    private String location;
    private Integer capacity;
    private Integer reservedCount;
    private BigDecimal ticketPrice;
    private EventStatus status;

    public Event(Long id, String title, String location, Integer capacity, Integer reservedCount, BigDecimal ticketPrice, EventStatus status) {
        super(id);
        this.title = title;
        this.location = location;
        this.capacity = capacity;
        this.reservedCount = reservedCount;
        this.ticketPrice = ticketPrice;
        this.status = status;
    }

    public Event(Long id, String title, String location, Integer capacity, BigDecimal ticketPrice) {
        super(id);
        this.title = title;
        this.location = location;
        this.capacity = capacity;
        this.ticketPrice = ticketPrice;
    }

    public Event(String title, String location, Integer capacity, BigDecimal ticketPrice) {
        this.title = title;
        this.location = location;
        this.capacity = capacity;
        this.ticketPrice = ticketPrice;
    }


    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Integer getReservedCount() {
        return reservedCount;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public EventStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return String.format("""
                ID: %d | Title: %s
                Location: %s | Reserved: %d/%d
                Price: %.2f$ | Status: %s
                """, this.getId(), title, location, reservedCount, capacity, ticketPrice, status.toString());
    }
}
