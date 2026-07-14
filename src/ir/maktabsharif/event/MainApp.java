package ir.maktabsharif.event;

import ir.maktabsharif.event.enums.EventStatus;
import ir.maktabsharif.event.enums.ReservationStatus;
import ir.maktabsharif.event.exception.*;
import ir.maktabsharif.event.model.Event;
import ir.maktabsharif.event.model.Reservation;
import ir.maktabsharif.event.service.event.EventService;
import ir.maktabsharif.event.service.event.impl.EventServiceImpl;
import ir.maktabsharif.event.service.reservation.ReservationService;
import ir.maktabsharif.event.service.reservation.impl.ReservationServiceImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MainApp {
    static void main() {
        EventService eventService = new EventServiceImpl();
        ReservationService reservationService = new ReservationServiceImpl();

        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("""
                    ==== Event Ticket Reservation System ====
                    
                    1. Create Event
                    2. Show All Events
                    3. Find Event
                    4. Update Event
                    5. Cancel Event
                    6. Create Reservation
                    7. Show All Reservations
                    8. Find Reservation
                    9. Update Reservation
                    10. Cancel Reservation
                    11. Reports
                    0. Exit
                    """);

            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    try {
                        System.out.println("\n---- Creating Event ----\n");
                        System.out.print("Enter Event's Title: ");
                        String title = input.nextLine();
                        System.out.print("Enter " + title + "'s Location: ");
                        String location = input.nextLine();
                        System.out.print("Enter " + title + "'s Capacity: ");
                        Integer capacity = input.nextInt();
                        input.nextLine();
                        System.out.print("Enter " + title + "'s Ticket Price: ");
                        BigDecimal ticketPrice = input.nextBigDecimal();
                        input.nextLine();

                        Long id = eventService.register(new Event(title, location, capacity, ticketPrice));
                        System.out.println("\nEvent Created Successfully!\nID: " + id);
                    }
                    catch (InvalidDataException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        System.out.println("\n---- All Events ----\n");
                        eventService.getAll().forEach(System.out::println);
                    }
                    catch (EventNotFoundException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        System.out.println("\n---- Finding Event By ID ----\n");
                        System.out.print("Enter Event's ID: ");
                        Long id = input.nextLong();
                        input.nextLine();
                        System.out.println(eventService.getById(id));
                    }
                    catch (EventNotFoundException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        System.out.println("\n---- Updating Event ----\n");
                        System.out.print("Enter Event ID: ");
                        Long id = input.nextLong();
                        input.nextLine();
                        System.out.print("Enter New Title: ");
                        String newTitle = input.nextLine();
                        System.out.print("Enter New Location: ");
                        String newLocation = input.nextLine();
                        System.out.print("Enter New Capacity: ");
                        Integer newCapacity = input.nextInt();
                        input.nextLine();
                        System.out.print("Enter New Ticket Price: ");
                        BigDecimal newTicketPrice = input.nextBigDecimal();
                        input.nextLine();

                        Event newEvent = eventService.change(new Event(id, newTitle, newLocation, newCapacity, newTicketPrice));


                        System.out.println("\nEvent Updated Successfully!" + newEvent);
                    }
                    catch (EventNotFoundException | InvalidDataException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 5:
                    try {
                        System.out.println("\n---- Canceling Event ----\n");
                        System.out.print("Enter Event's ID: ");
                        Long id = input.nextLong();
                        input.nextLine();

                        id = eventService.cancel(id);

                        System.out.println("\nEvent Cancelled Successfully With ID: " + id);
                    }
                    catch (EventNotFoundException | InvalidDataException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 6:
                    try {
                        System.out.println("\n---- Creating Reservation ----\n");
                        System.out.print("Enter Customer Name: ");
                        String customerName = input.nextLine();
                        System.out.print("Enter Customer Phone Number: ");
                        String customerPhone = input.nextLine();
                        System.out.print("Enter Event ID: ");
                        Long eventId = input.nextLong();
                        input.nextLine();
                        System.out.print("Enter Ticket Count: ");
                        Integer ticketCount = input.nextInt();
                        input.nextLine();

                        Long id = reservationService.register(new Reservation(customerName, customerPhone, eventId, ticketCount, LocalDate.now()));
                        System.out.println("\nReservation Created Successfully!\nID: " + id);
                    }
                    catch (EventNotFoundException | EventCancelledException | CapacityExceededException | InvalidDataException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 7:
                    try {
                        System.out.println("\n---- All Reservations ----\n");
                        reservationService.getAll().forEach(System.out::println);
                    }
                    catch (ReservationNotFoundException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 8:
                    try {
                        System.out.println("\n---- Finding Reservation By ID ----\n");
                        System.out.print("Enter Reservation's ID: ");
                        Long id = input.nextLong();
                        input.nextLine();
                        System.out.println(reservationService.getById(id));
                    }
                    catch (ReservationNotFoundException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 9:
                    try {
                        System.out.println("\n---- Updating Reservation ----\n");
                        System.out.print("Enter Reservation's ID: ");
                        Long id = input.nextLong();
                        System.out.print("Enter New Customer Name: ");
                        String newName = input.nextLine();
                        System.out.print("Enter New Customer Phone Number: ");
                        String newPhone = input.nextLine();
                        System.out.print("Enter New Event ID: ");
                        Long newEventId = input.nextLong();
                        input.nextLine();
                        System.out.print("Enter New Ticket Count: ");
                        Integer newTicketCount = input.nextInt();
                        input.nextLine();

                        Reservation newReservation = reservationService.change(new Reservation(id, newName, newPhone, newEventId, newTicketCount));

                        System.out.println("\nReservation Updated Successfully!" + newReservation);
                    }
                    catch (EventNotFoundException | ReservationNotFoundException | InvalidDataException | EventCancelledException | CapacityExceededException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 10:
                    try {
                        System.out.println("\n---- Canceling Reservation ----\n");
                        System.out.print("Enter Reservation's ID: ");
                        Long id = input.nextLong();
                        input.nextLine();

                        id = reservationService.cancel(id);

                        System.out.println("\nReservation Cancelled Successfully With ID: " + id);
                    }
                    catch (EventNotFoundException | InvalidDataException | ReservationNotFoundException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 11:
                    try {
                        System.out.println("\n---- Reports ----\n");

                        List<Event> events = eventService.getAll();

                        long activeEventCount = events.stream()
                                .filter(event -> event.getStatus().equals(EventStatus.ACTIVE))
                                .count();
                        System.out.println("\nActive Event Count: " + activeEventCount + "\n");

                        BigDecimal maxPrice = events.stream()
                                .map(Event::getTicketPrice)
                                .max(BigDecimal::compareTo)
                                .orElse(BigDecimal.ZERO);
                        List<Event> mostExpensiveEvents = events.stream()
                                .filter(e -> e.getTicketPrice().compareTo(maxPrice) == 0)
                                .toList();
                        System.out.println("\nMost Expensive Events: ");
                        mostExpensiveEvents.forEach(System.out::println);

                        List<BigDecimal> prices = events.stream().map(Event::getTicketPrice).toList();
                        BigDecimal sumPrice = BigDecimal.ZERO;
                        for (BigDecimal price : prices) {
                            sumPrice = sumPrice.add(price);
                        }
//                        BigDecimal sumPrice = events.stream()
//                                .map(Event::getTicketPrice)
//                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                        BigDecimal avgPrice = sumPrice.divide(
                                BigDecimal.valueOf(events.size()),
                                2, RoundingMode.HALF_UP
                        );
                        System.out.println("\nAverage Ticket Price: " + avgPrice + "\n");

                        try {
                            List<Reservation> reservations = reservationService.getAll();

                            List<Reservation> activeReservations = reservations.stream()
                                    .filter(reservation -> reservation.getStatus().equals(ReservationStatus.ACTIVE))
                                    .toList();
                            System.out.println("\nActive Reservations: ");
                            activeReservations.forEach(System.out::println);
                        }
                        catch (ReservationNotFoundException e) {
                            System.err.println(e.getMessage());
                        }

                        List<Event> fullyBookedEvents = events.stream()
                                .filter(event -> event.getCapacity().equals(event.getReservedCount()))
                                .toList();
                        System.out.println("\nFully Booked Events: ");
                        fullyBookedEvents.forEach(System.out::println);
                    }
                    catch (EventNotFoundException e) {
                        System.err.println(e.getMessage());
                    }
            }
        }
    }
}
