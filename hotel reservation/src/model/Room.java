package model;

import java.util.Date;
import java.util.List;

public class Room implements IRoom{
    private String roomNumber;
    private Double price;
    private RoomType enumeration;
    private boolean isFree;
    private boolean isAvailable;

    public Room(String roomNumber, Double price, RoomType enumeration){
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
        this.isFree = false;
        this.isAvailable = true;
    }

    public void setFree() {
        this.isFree = true;
    }

    public void setAvailability(boolean isAvailable){
        this.isAvailable = isAvailable;
    }

    @Override
    public String getRoomNumber(){
        return  roomNumber;
    }

    @Override
    public Double getRoomPrice(){
        return price;
     }

    @Override
    public RoomType getRoomType(){
        return enumeration;
    }
    @Override
    public boolean isFree(){
        return isFree;
    }

    @Override
    public boolean isAvailable(){
        return isAvailable;
    }


    @Override
    public String toString() {
        return "RoomNumber: " + getRoomNumber() + '\n' +
                "Price: " + getRoomPrice() + '\n' +
                "Room Type: " + getRoomType();
    }
}
