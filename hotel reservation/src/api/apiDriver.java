package api;

//public class apiDriver {
//    AdminResource adminResource = new AdminResource()
//}
import api.AdminResource;
import model.*;
import service.CustomerService;
import service.ReservationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class apiDriver {
    public static void main(String[] args) {
        /*
        // Instantiate CustomerService
        CustomerService customerService = CustomerService.getInstance();

        // Instantiate ReservationService
        ReservationService reservationService = ReservationService.getInstance();

        // Instantiate AdminResource
        AdminResource adminResource = new AdminResource(customerService, reservationService);

        // Add some rooms
        List<IRoom> rooms = new ArrayList<>();
        rooms.add(new Room("101", 100.0, RoomType.SINGLE));
        rooms.add(new FreeRoom("102", 150.0, RoomType.DOUBLE));
        rooms.add(new FreeRoom("103", 200.0, RoomType.DOUBLE));
        adminResource.addRoom(rooms);

        // Retrieve all rooms
        Collection<IRoom> allRooms = adminResource.getAllRooms();
        System.out.println("All Rooms:");
        for (IRoom room : allRooms) {
            System.out.println(room);
        }

        // Retrieve all customers
        Collection<Customer> allCustomers = adminResource.getAllCustomers();
        customerService.addCustomer("john.doe@example.com", "John", "Doe");
        customerService.addCustomer("jane.smith@example.com", "Jane", "Smith");
        System.out.println("\nAll Customers:");
        for (Customer customer : allCustomers) {
            System.out.println(customer);
        }

        // Display all reservations
        Customer johnDoe = customerService.getCustomer("john.doe@example.com");
        IRoom room = reservationService.getARoom("101");
        Date checkInDate = new Date(2024, 2, 9);
        Date checkOutDate = new Date(2024, 2, 22);
        reservationService.reserveARoom(johnDoe, room, checkInDate, checkOutDate);

        System.out.println("\nAll Reservations:");
        adminResource.displayAllReservations();

         */

        // Instantiate CustomerService
        CustomerService customerService = CustomerService.getInstance();

        // Instantiate ReservationService
        ReservationService reservationService = ReservationService.getInstance();

        // Instantiate HotelResource
        HotelResource hotelResource = new HotelResource(customerService, reservationService);

        // Create some customers
        hotelResource.createACustomer("john.doe@example.com", "John", "Doe");
        hotelResource.createACustomer("jane.smith@example.com", "Jane", "Smith");
        System.out.println("--------");
        System.out.println(customerService.getCustomer("john.doe@example.com"));
        System.out.println("--------");

        // Add some rooms
        reservationService.addRoom(new Room("101", 100.0, RoomType.SINGLE));
        reservationService.addRoom(new Room("102", 150.0, RoomType.DOUBLE));
        reservationService.addRoom(new Room("103", 200.0, RoomType.DOUBLE));

        // Get a room
        IRoom room = hotelResource.getRoom("101");

        // Book a room for a customer
        Date checkInDate = new Date(2024, 2, 9);
        Date checkOutDate = new Date(2024, 2, 22);
        hotelResource.bookARoom("john.doe@example.com", room, checkInDate, checkOutDate);

        // Get customer reservations
        System.out.println("Customer Reservations:");
        for (Reservation reservation : hotelResource.getCustomerReservations("john.doe@example.com")) {
            System.out.println(reservation);
        }

        // Find available rooms
        System.out.println("\nAvailable Rooms:");
        for (IRoom availableRoom : hotelResource.findARoom(checkInDate, checkOutDate)) {
            System.out.println(availableRoom);
        }




    }
}
