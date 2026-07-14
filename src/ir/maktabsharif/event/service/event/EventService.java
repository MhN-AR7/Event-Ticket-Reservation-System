package ir.maktabsharif.event.service.event;

import ir.maktabsharif.event.model.Event;
import ir.maktabsharif.event.service.GenericService;

import java.util.List;

public interface EventService extends GenericService<Event> {
    Integer getActiveEventCount();
    List<Event> getMostExpensive();
}
