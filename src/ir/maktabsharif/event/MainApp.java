package ir.maktabsharif.event;

import ir.maktabsharif.event.service.event.EventService;
import ir.maktabsharif.event.service.event.impl.EventServiceImpl;
import ir.maktabsharif.event.service.reservation.ReservationService;
import ir.maktabsharif.event.service.reservation.impl.ReservationServiceImpl;

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
                    3. Update Event
                    4. Cancel Event
                    5. Create Reservation
                    6. Cancel Reservation
                    7. Show All Reservations
                    8. Reports
                    9. Exit
                    """);

            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:

            }
        }
    }
}
