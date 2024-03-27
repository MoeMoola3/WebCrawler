package service;

import model.*;

import java.util.*;

public class ServiceDriver {
    public static void main(String[] args) {

        /**
         *  CUSTOMERSERVICE
         */

        // Instantiate CustomerService
//        CustomerService customerService = CustomerService.getInstance();

        // Add customers
//        customerService.addCustomer("john.doe@example.com", "John", "Doe");
//        customerService.addCustomer("jane.smith@example.com", "Jane", "Smith");

//        // Retrieve a customer
//        Customer johnDoe = customerService.getCustomer("john.doe@example.com");
//        System.out.println("Retrieved customer: " + johnDoe.getFirstName());
//
//        // Retrieve all customers
//        System.out.println("All customers:");
//        for (Customer customer : customerService.getAllCustomers()) {
//            System.out.println(customer);
//        }

        /**
         *  RESERVATIONSERVICE
         */

//        ReservationService reservationService = ReservationService.getInstance();

/*
        //addRoom & getARoom
        IRoom room1 = new Room("101", 100.0, RoomType.SINGLE);
        IRoom room2 = new FreeRoom("102", 150.0, RoomType.DOUBLE);
        IRoom room3 = new FreeRoom("103", 200.0, RoomType.DOUBLE);
        reservationService.addRoom(room1);
        reservationService.addRoom(room2);
        reservationService.addRoom(room3);
        IRoom retrievedRoom = reservationService.getARoom("101");
//        System.out.println("Retrieved room: " + retrievedRoom.getRoomNumber() + ", Price: " + retrievedRoom.getRoomPrice());

        //reserveARoom
        Date checkInDate = new Date(2024, 2, 9);
        Date checkOutDate = new Date(2024, 2, 22);
        Reservation reservation = new Reservation(johnDoe, room1,checkInDate, checkOutDate);
        reservationService.reserveARoom(johnDoe, room1, checkInDate, checkOutDate);
        reservationService.reserveARoom(customerService.getCustomer("jane.smith@example.com"), room2, checkInDate, checkOutDate);
//        System.out.println(reservation);

        //findRooms
//        Collection<IRoom> availableRooms = reservationService.findRooms(checkInDate, checkOutDate);
//        System.out.println("Available Rooms:");
//        for (IRoom room : availableRooms) {
//            System.out.println(room);
//        }

        //getCustomersReservation
        Collection<Reservation> custRes = reservationService.getCustomersReservation(johnDoe);
//        System.out.println(custRes);

        //printAllReservation
        reservationService.printAllReservation();
*/



//        Calendar checkInCalendar = Calendar.getInstance();
//        checkInCalendar.set(2024, Calendar.FEBRUARY, 14); // Example check-in date
//        Date checkInDate = checkInCalendar.getTime();
//
//        Calendar checkOutCalendar = Calendar.getInstance();
//        checkOutCalendar.set(2024, Calendar.FEBRUARY, 20); // Example check-out date
//        Date checkOutDate = checkOutCalendar.getTime();
//
//        HashSet<Date> bookedDates = reservationService.bookDates(checkInDate, checkOutDate);
//        System.out.println("Booked Dates:");
//        for (Date date : bookedDates) {
//            System.out.println(date);
//        }
//
//        Calendar startDateCalendar = Calendar.getInstance();
//        startDateCalendar.set(2024, Calendar.FEBRUARY, 21); // Example start date of reservation check
//        Date startDate = startDateCalendar.getTime();
//
//        Calendar endDateCalendar = Calendar.getInstance();
//        endDateCalendar.set(2024, Calendar.FEBRUARY, 23); // Example end date of reservation check
//        Date endDate = endDateCalendar.getTime();
//
//        reservationService.checkReservationDates(startDate, endDate);
        ReservationService reservationService = ReservationService.getInstance();

        // Create a Customer
        Customer customer1 = new Customer("John", "Doe", "john@example.com");
        Customer customer2 = new Customer("Alice", "Smith", "alice@example.com");

        // Create a Room
        IRoom room1 = new Room("101", 100.0, RoomType.SINGLE);
        IRoom room2 = new Room("102", 150.0, RoomType.DOUBLE);

        // Define check-in and check-out dates
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.FEBRUARY, 21); // Check-in tomorrow
        Date checkInDate1 = calendar.getTime();
        calendar.set(2024, Calendar.FEBRUARY, 23); // Check-out after 3 days
        Date checkOutDate1 = calendar.getTime();

        calendar.set(2024, Calendar.FEBRUARY, 24); // Check-in tomorrow
        Date checkInDate2 = calendar.getTime();
        calendar.set(2024, Calendar.FEBRUARY, 26); // Check-out after 2 days
        Date checkOutDate2 = calendar.getTime();

        // Reserve rooms
        Reservation reservation1 = reservationService.reserveARoom(customer1, room1, checkInDate1, checkOutDate1);
        Reservation reservation2 = reservationService.reserveARoom(customer2, room1, checkInDate2, checkOutDate2);

        // Print out the whole HashMap
        System.out.println("Room Reservation Dates:");
        for (Map.Entry<String, Set<Date>> entry : reservationService.getRoomReservationDates().entrySet()) {
            String roomNumber = entry.getKey();
            Set<Date> reservationDates = entry.getValue();
            System.out.println("Room Number: " + roomNumber);
            System.out.println("Reservation Dates: " + reservationDates);
            System.out.println("-------------------------------------------");
        }
    }




}

