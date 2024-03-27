package model;

public interface  IRoom {
    public String getRoomNumber();
    public Double getRoomPrice();
    public RoomType getRoomType();
    public boolean isFree();
    public boolean isAvailable();
    public void setAvailability(boolean isAvailable);
}
