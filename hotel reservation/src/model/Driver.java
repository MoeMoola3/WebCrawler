package model;

import java.util.Date;

public class Driver {
    public static void main(String[] args){
        Customer customer = new Customer("Sean", "Kingston", "j@domain.com");
        System.out.println(customer);
        System.out.println("-----------------------------");

        Room room = new Room("101", 55.0, RoomType.DOUBLE);
        System.out.println(room);
        System.out.println("-----------------------------");

        FreeRoom freeRoom = new FreeRoom("101", 55.0, RoomType.DOUBLE);
        System.out.println(freeRoom);
        System.out.println("-----------------------------");

        Date checkInDate = new Date(2024, 2, 9);
        Date checkOutDate = new Date(2024, 2, 22);
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        System.out.println(reservation);
        System.out.println("-----------------------------");

    }
}
