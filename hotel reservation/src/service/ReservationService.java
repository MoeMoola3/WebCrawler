package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.SimpleDateFormat;
import java.util.*;

public class ReservationService {
    private static final ReservationService INSTANCE = new ReservationService();
    // Hashmap allows for fast retrieval of rooms
    private static Map<String, IRoom> rooms = new HashMap<>();
    // Hashset allows for uniqueness of reservations
    private static Set<Reservation> reservations = new HashSet<>();
    private static HashSet<Date> dateHashSet = new HashSet<>();
    private static Map<String, Set<Date>> roomReservationDates = new HashMap<>();

    private ReservationService(){}

    public void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }


    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        boolean isAvailable = checkReservationDates(room.getRoomNumber(), checkInDate, checkOutDate);
        if(isAvailable) {
            bookDates(room.getRoomNumber(), checkInDate, checkOutDate);
//            room.setAvailability(false);
            reservations.add(reservation);
        } else {
            return reservation;
        }
        return reservation;
    }


    public boolean checkReservationDates(String roomNumber, Date startDate, Date endDate) {
        Set<Date> reservationDates = roomReservationDates.getOrDefault(roomNumber, new HashSet<>());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        while (!calendar.getTime().after(endDate)) {
            if (reservationDates.contains(calendar.getTime())) {
                System.out.println("This room is not available on " + calendar.getTime());
                return false;
            }
            calendar.add(Calendar.DATE, 1); // Move to the next day
        }
        System.out.println("This room is available during this time period");
        System.out.println("Reservation successful.");
        return true;
    }

    public void bookDates(String roomNumber, Date checkInDate, Date checkOutDate) {
        Set<Date> reservationDates = roomReservationDates.getOrDefault(roomNumber, new HashSet<>());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkInDate);
        while (!calendar.getTime().after(checkOutDate)) {
            reservationDates.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        roomReservationDates.put(roomNumber, reservationDates);
    }



    public Collection<IRoom> findRooms_init(Date checkInDate, Date checkOutDate) {
        List<IRoom> availableRooms = new ArrayList<>();
        for (IRoom room : rooms.values()) {
            if (room.isAvailable()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> availableRooms = new ArrayList<>();

        // Iterate through all rooms
        for (IRoom room : rooms.values()) {
            boolean isAvailable = true; // Assume room is available initially

            // Get reservation dates for this room
            Set<Date> reservationDates = roomReservationDates.getOrDefault(room.getRoomNumber(), new HashSet<>());

            // Iterate through the time period between check-in and check-out dates
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(checkInDate);
            while (!calendar.getTime().after(checkOutDate)) {
                // Check if any reservation date conflicts with the current date in the loop
                if (reservationDates.contains(calendar.getTime())) {
                    isAvailable = false; // Room is not available for this time period
                    break; // No need to continue checking, we already found a conflict
                }
                calendar.add(Calendar.DATE, 1); // Move to the next day
            }

            // If room is available, add it to the list of available rooms
            if (isAvailable) {
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }



    public Collection<Reservation> getCustomersReservation(Customer customer) {
        List<Reservation> customerReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().equals(customer)) {
                customerReservations.add(reservation);
            }
        }
        return customerReservations;
    }

    public void printAllReservation() {
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
//            if(reservations.isEmpty()){
//                System.out.println("No Reservations");
//            }
        }
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    public static ReservationService getInstance() {
        return INSTANCE;
    }

    public Map<String, Set<Date>> getRoomReservationDates() {
        return roomReservationDates;
    }
}
