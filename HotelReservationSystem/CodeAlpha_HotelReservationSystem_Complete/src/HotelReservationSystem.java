import java.util.ArrayList;
import java.util.Scanner;

public class HotelReservationSystem {

    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Reservation> reservations = new ArrayList<>();

    static int nextReservationId = 1001;

    public static void main(String[] args) {
        loadRooms();
        loadSavedReservations();

        int choice;

        do {
            displayMenu();
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    displayAvailableRooms();
                    break;
                case 2:
                    bookRoom();
                    break;
                case 3:
                    displayReservations();
                    break;
                case 4:
                    cancelReservation();
                    break;
                case 5:
                    searchRoomsByCategory();
                    break;
                case 6:
                    System.out.println("\nThank you for using the Hotel Reservation System!");
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        } while (choice != 6);

        scanner.close();
    }

    static void loadRooms() {
        rooms.add(new Room(101, "Standard", 2000));
        rooms.add(new Room(102, "Standard", 2000));
        rooms.add(new Room(201, "Deluxe", 3500));
        rooms.add(new Room(202, "Deluxe", 3500));
        rooms.add(new Room(301, "Suite", 5500));
        rooms.add(new Room(302, "Suite", 5500));
    }

    static void loadSavedReservations() {
        ArrayList<String[]> records = FileManager.loadReservations();

        for (String[] record : records) {
            try {
                int id = Integer.parseInt(record[0]);
                String guestName = record[1];
                int roomNumber = Integer.parseInt(record[2]);
                int nights = Integer.parseInt(record[3]);

                Room room = findRoom(roomNumber);

                if (room != null && room.isAvailable() && nights > 0) {
                    Reservation reservation =
                            new Reservation(id, guestName, room, nights);

                    reservations.add(reservation);
                    room.setAvailable(false);

                    if (id >= nextReservationId) {
                        nextReservationId = id + 1;
                    }
                }
            } catch (NumberFormatException ignored) {
                // Ignore invalid saved records.
            }
        }
    }

    static void displayMenu() {
        System.out.println("\n==============================================");
        System.out.println("          HOTEL RESERVATION SYSTEM");
        System.out.println("==============================================");
        System.out.println("1. View Available Rooms");
        System.out.println("2. Book a Room");
        System.out.println("3. View Reservations");
        System.out.println("4. Cancel Reservation");
        System.out.println("5. Search Rooms by Category");
        System.out.println("6. Exit");
        System.out.println("==============================================");
    }

    static void displayAvailableRooms() {
        System.out.println("\n--------------- AVAILABLE ROOMS ----------------");
        System.out.printf("%-10s %-15s %-15s%n",
                "Room", "Category", "Price/Night");
        System.out.println("-----------------------------------------------");

        boolean found = false;

        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.printf("%-10d %-15s %.2f%n",
                        room.getRoomNumber(),
                        room.getCategory(),
                        room.getPricePerNight());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms are currently available.");
        }
    }

    static void bookRoom() {
        displayAvailableRooms();

        int roomNumber = readInt("\nEnter room number: ");
        Room room = findRoom(roomNumber);

        if (room == null) {
            System.out.println("Room not found.");
            return;
        }

        if (!room.isAvailable()) {
            System.out.println("This room is already booked.");
            return;
        }

        String guestName = readText("Enter guest name: ");

        if (guestName.isEmpty()) {
            System.out.println("Guest name cannot be empty.");
            return;
        }

        int nights = readInt("Enter number of nights: ");

        if (nights <= 0) {
            System.out.println("Number of nights must be greater than zero.");
            return;
        }

        Reservation reservation =
                new Reservation(nextReservationId++, guestName, room, nights);

        reservations.add(reservation);
        room.setAvailable(false);
        FileManager.saveReservations(reservations);

        System.out.println("\nReservation created successfully!");
        displayReservationDetails(reservation);
    }

    static void displayReservations() {
        System.out.println("\n---------------- RESERVATIONS ----------------");

        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }

        System.out.printf("%-8s %-18s %-8s %-12s %-10s%n",
                "ID", "Guest", "Room", "Category", "Nights");
        System.out.println("------------------------------------------------------------");

        for (Reservation reservation : reservations) {
            System.out.printf("%-8d %-18s %-8d %-12s %-10d%n",
                    reservation.getReservationId(),
                    reservation.getGuestName(),
                    reservation.getRoom().getRoomNumber(),
                    reservation.getRoom().getCategory(),
                    reservation.getNights());
        }

        System.out.println("\nBooking Details:");

        for (Reservation reservation : reservations) {
            System.out.printf(
                    "ID %d | %s | Room %d | %d nights | Total: %.2f%n",
                    reservation.getReservationId(),
                    reservation.getGuestName(),
                    reservation.getRoom().getRoomNumber(),
                    reservation.getNights(),
                    reservation.getTotalAmount()
            );
        }
    }

    static void displayReservationDetails(Reservation reservation) {
        System.out.println("----------------------------------------------");
        System.out.println("Reservation ID : " + reservation.getReservationId());
        System.out.println("Guest Name     : " + reservation.getGuestName());
        System.out.println("Room Number    : " + reservation.getRoom().getRoomNumber());
        System.out.println("Room Category  : " + reservation.getRoom().getCategory());
        System.out.println("Nights         : " + reservation.getNights());
        System.out.printf("Price/Night    : %.2f%n",
                reservation.getRoom().getPricePerNight());
        System.out.printf("Total Amount   : %.2f%n",
                reservation.getTotalAmount());
        System.out.println("----------------------------------------------");
        System.out.println("Payment Status : SIMULATED / NOT PROCESSED");
    }

    static void cancelReservation() {
        if (reservations.isEmpty()) {
            System.out.println("\nNo reservations available to cancel.");
            return;
        }

        displayReservations();

        int reservationId = readInt("\nEnter reservation ID to cancel: ");
        Reservation reservation = findReservation(reservationId);

        if (reservation == null) {
            System.out.println("Reservation not found.");
            return;
        }

        reservation.getRoom().setAvailable(true);
        reservations.remove(reservation);
        FileManager.saveReservations(reservations);

        System.out.println("Reservation cancelled successfully.");
        System.out.println("Room " + reservation.getRoom().getRoomNumber()
                + " is now available.");
    }

    static void searchRoomsByCategory() {
        String category = readText(
                "\nEnter category (Standard/Deluxe/Suite): ");

        boolean found = false;

        System.out.println("\n------------- SEARCH RESULTS -------------");

        for (Room room : rooms) {
            if (room.getCategory().equalsIgnoreCase(category)
                    && room.isAvailable()) {

                System.out.printf(
                        "Room %d | %s | %.2f per night%n",
                        room.getRoomNumber(),
                        room.getCategory(),
                        room.getPricePerNight()
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println("No available rooms found in this category.");
        }
    }

    static Room findRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    static Reservation findReservation(int reservationId) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationId() == reservationId) {
                return reservation;
            }
        }
        return null;
    }

    static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (Exception e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }

    static String readText(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }
}
