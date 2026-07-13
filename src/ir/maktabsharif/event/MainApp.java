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

            }
        }
    }
}
