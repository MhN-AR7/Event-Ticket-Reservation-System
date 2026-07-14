package ir.maktabsharif.event;

import ir.maktabsharif.event.exception.EventNotFoundException;
import ir.maktabsharif.event.exception.InvalidDataException;
import ir.maktabsharif.event.model.Event;
import ir.maktabsharif.event.service.event.EventService;
import ir.maktabsharif.event.service.event.impl.EventServiceImpl;
import ir.maktabsharif.event.service.reservation.ReservationService;
import ir.maktabsharif.event.service.reservation.impl.ReservationServiceImpl;

import java.math.BigDecimal;
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
            }
        }
    }
}
