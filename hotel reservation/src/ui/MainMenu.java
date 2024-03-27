package ui;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    static CustomerService customerService = CustomerService.getInstance();

    static ReservationService reservationService = ReservationService.getInstance();

    private static final HotelResource hotelResource = new HotelResource(customerService,reservationService);
    private static final int RECOMMENDED_DAYS = 7;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean mainMenuFlag = true;
        do {
            System.out.print("\nWelcome to the Hotel Reservation Application\n" +
                    "--------------------------------------------\n" +
                    "1. Find and reserve a room\n" +
                    "2. See my reservations\n" +
                    "3. Create an Account\n" +
                    "4. Admin\n" +
                    "5. Exit\n" +
                    "--------------------------------------------\n" +
                    "Please select a number for the menu option:\n");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // Consume invalid input
                continue; // Restart the loop
            }

            switch (choice) {
                case 1:
                    findAndReserveRoom(scanner);
                    break;
                case 2:
                    checkReservation(scanner);
                    break;
                case 3:
                    createAnAccount(scanner);
                    break;
                case 4:
                    adminMenu(scanner);
                    break;
                case 5:
                    exitMenu(scanner);
                    mainMenuFlag = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid menu option.");
            }
        } while (mainMenuFlag);

        scanner.close();
    }

    private static void findAndReserveRoom(Scanner scanner) {
        System.out.println("Please enter check-in date (MM/dd/yyyy): ");
        Date checkInDate = getDateInput(scanner);
        System.out.println("Please enter check-out date (MM/dd/yyyy): ");
        Date checkOutDate = getDateInput(scanner);

        Collection<IRoom> availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);

        if (availableRooms.isEmpty()) {
            System.out.println("No rooms available for the selected dates.");

            // Calculate recommended dates
            Calendar recommendedCheckIn = Calendar.getInstance();
            recommendedCheckIn.setTime(checkInDate);
            recommendedCheckIn.add(Calendar.DAY_OF_MONTH, RECOMMENDED_DAYS);
            Date recommendedCheckInDate = recommendedCheckIn.getTime();

            Calendar recommendedCheckOut = Calendar.getInstance();
            recommendedCheckOut.setTime(checkOutDate);
            recommendedCheckOut.add(Calendar.DAY_OF_MONTH, RECOMMENDED_DAYS);
            Date recommendedCheckOutDate = recommendedCheckOut.getTime();

            Collection<IRoom> recommendedRooms = hotelResource.findARoom(recommendedCheckInDate, recommendedCheckOutDate);

            if (recommendedRooms.isEmpty()) {
                System.out.println("No recommended rooms found for alternative dates.");
            } else {
                System.out.println("Recommended Rooms for alternative dates " + dateFormat.format(recommendedCheckInDate) +
                        " to " + dateFormat.format(recommendedCheckOutDate) + ":");
                for (IRoom room : recommendedRooms) {
                    System.out.println(room);
                }
            }


        } else {
            System.out.println("Available Rooms:");
            for (IRoom room : availableRooms) {
                System.out.println(room);
            }
            System.out.println("Enter room number to reserve: ");
            String roomNumber = scanner.nextLine();

            IRoom selectedRoom = null;
            for (IRoom room : availableRooms) {
                if (room.getRoomNumber().equals(roomNumber)) {
                    selectedRoom = room;
                    break;
                }
            }

            if (selectedRoom != null) {
                System.out.println("Please enter your email: ");
                String customerEmail = scanner.nextLine();
                Customer customer = hotelResource.getCustomer(customerEmail);
                if (customer == null) {
                    System.out.println("Customer does not exist. Please create an account first.");
                    return;
                }

                Reservation reservation = hotelResource.bookARoom(customerEmail, selectedRoom, checkInDate, checkOutDate);

            } else {
                System.out.println("Invalid room number.");
            }
        }
    }



    private static void checkReservation(Scanner scanner) {
        try {
            System.out.println("Please enter your email: ");
            String email = scanner.nextLine();
            if (!isValidEmail(email)) {
                throw new IllegalArgumentException("Invalid email format.");
            }

            Customer customer = hotelResource.getCustomer(email);
            if (customer == null) {
                System.out.println("Customer with email " + email + " does not exist.");
                return;
            }

            Collection<Reservation> customerReservations = hotelResource.getCustomerReservations(email);
            if (customerReservations.isEmpty()) {
                System.out.println("You have no reservations.");
            } else {
//                System.out.println("Your Reservations:");
                for (Reservation reservation : customerReservations) {
                    System.out.println(reservation);
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private static void createAnAccount(Scanner scanner){

        try {
            System.out.println("Please enter your email: ");
            String email = scanner.nextLine();
            if (!isValidEmail(email)) {
                throw new IllegalArgumentException("Invalid email format.");
            }

            System.out.println("Please enter your first name: ");
            String firstName = scanner.nextLine();
            if (!isValidName(firstName)) {
                throw new IllegalArgumentException("Invalid first name format.");
            }

            System.out.println("Please enter your last name: ");
            String lastName = scanner.nextLine();
            if (!isValidName(lastName)) {
                throw new IllegalArgumentException("Invalid last name format.");
            }

            hotelResource.createACustomer(email, firstName, lastName);

            System.out.println("Account created successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private static void adminMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            AdminMenu.getMenu();

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        AdminMenu.seeAllCustomers();
                        break;
                    case 2:
                        AdminMenu.seeAllRooms();
                        break;
                    case 3:
                        AdminMenu.seeAllReservations();
                        break;
                    case 4:
                        List<IRoom> roomsToAdd = AdminMenu.addRoom(scanner);
                        break;
                    case 5:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please select a valid menu option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // Consume invalid input
            }
        }
    }

    private static void exitMenu(Scanner scanner) {}

    private static Date getDateInput(Scanner scanner) {
        Date date = null;
        while (date == null) {
            try {
                date = dateFormat.parse(scanner.nextLine());
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter date in MM/dd/yyyy format.");
            }
        }
        return date;
    }
    private static boolean isValidName(String name) {

        return name != null && !name.trim().isEmpty();
    }
    private static boolean isValidEmail(String email) {

        return email != null && email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }


}
