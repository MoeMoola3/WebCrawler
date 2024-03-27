package ui;

import api.AdminResource;
import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import service.CustomerService;
import service.ReservationService;

public class AdminMenu {

    static CustomerService customerService = CustomerService.getInstance();
    static ReservationService reservationService = ReservationService.getInstance();
    private static final AdminResource adminResource = new AdminResource(customerService, reservationService);

    private static void printMenu() {
        System.out.print("\nAdmin Menu\n" +
                "--------------------------------------------\n" +
                "1. See all Customers\n" +
                "2. See all Rooms\n" +
                "3. See all Reservations\n" +
                "4. Add a Room\n" +
                "5. Back to Main Menu\n" +
                "--------------------------------------------\n" +
                "Please select a number for the menu option:\n");
    }

    public static void getMenu() {
        printMenu();
    }

    public static void seeAllCustomers() {
        Collection<Customer> allCustomers = adminResource.getAllCustomers();

        if (allCustomers.isEmpty()) {
            System.out.println("No customers available.");
        } else {
            System.out.println("All Customers:");
            for (Customer customer : allCustomers) {
                System.out.println(customer);
            }
        }
    }

    public static void seeAllRooms() {
        Collection<IRoom> allRooms = adminResource.getAllRooms();

        if (allRooms.isEmpty()) {
            System.out.println("No rooms available.");
        } else {
            System.out.println("All Rooms:");
            for (IRoom room : allRooms) {
                System.out.println(room);
            }
        }
    }


//    public static Collection<model.Reservation> seeAllReservations() {
//        Collection<Reservation> allReservations = adminResource.displayAllReservations();
//
//        if (allReservations.isEmpty()) {
//            System.out.println("No reservations available.");
//        } else {
//            System.out.println("All Reservations:");
//            for (Reservation reservation : allReservations) {
//                System.out.println(reservation);
//            }
//        }
//        return allReservations;
//    }
    public static void seeAllReservations(){
        adminResource.displayAllReservations();
    }

    public static List<IRoom> addRoom(Scanner scanner) {
        List<IRoom> rooms = new ArrayList<>();

        boolean addAnotherRoom = true;
        while (addAnotherRoom) {
            try {
                System.out.println("Enter room number:");
                String roomNumber = scanner.nextLine();

                System.out.println("Enter price per night:");
                double roomPrice = getPriceInput(scanner);

                RoomType roomType = getRoomTypeInput(scanner);

                Room room = new Room(roomNumber, roomPrice, roomType);
                rooms.add(room);

                boolean validResponse;
                do {
                    validResponse = true;
                    if (!validResponse) {
                        System.out.println("Please enter 'y' (Yes) or 'n' (No)");
                    }
                    System.out.println("Would you like to add another room? (y/n)");
                    String additionalRooms = scanner.nextLine();
                    if (additionalRooms.equalsIgnoreCase("y")) {
                        addAnotherRoom = true;
                    } else if (additionalRooms.equalsIgnoreCase("n")) {
                        addAnotherRoom = false;
                    } else {
                        validResponse = false;
                    }
                } while (!validResponse);

                adminResource.addRoom(rooms);
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid price format. Please enter a valid price.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        return rooms;
    }

    private static double getPriceInput(Scanner scanner) throws NumberFormatException {
        double price = 0.0;
        while (true) {
            try {
                price = Double.parseDouble(scanner.nextLine());
                if (price <= 0) {
                    throw new IllegalArgumentException("Price must be greater than 0.");
                }
                break; // If parsing and validation successful, break out of the loop
            } catch (NumberFormatException e) {
                System.out.println("Invalid price format. Please enter a valid price.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return price;
    }

    private static RoomType getRoomTypeInput(Scanner scanner) {
        RoomType roomType = null;
        while (roomType == null) {
            try {
                System.out.println("Enter room type: 1 for single bed, 2 for double bed");
                int roomTypeChoice = Integer.parseInt(scanner.nextLine());
                switch (roomTypeChoice) {
                    case 1:
                        roomType = RoomType.SINGLE;
                        break;
                    case 2:
                        roomType = RoomType.DOUBLE;
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid room type choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return roomType;
    }





}
