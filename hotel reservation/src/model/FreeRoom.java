package model;

public class FreeRoom extends Room {

    public FreeRoom(String roomNumber, Double price, RoomType enumeration) {
        super(roomNumber, 0.0, enumeration);
        this.setFree();
    }

    @Override
    public String toString() {
        return "RoomNumber: " + getRoomNumber() + '\n' +
                "Price: " + getRoomPrice() + '\n' +
                "Room Type: " + getRoomType();
    }
}