package bookingkaraoke;

public class Room {
    private String roomId;
    private String type;
    private int capacity;
    private double pricePerHour;

    public Room(String roomId, String type, int capacity, double pricePerHour) {
        this.roomId = roomId;
        this.type = type;
        this.capacity = capacity;
        this.pricePerHour = pricePerHour;
    }

    // Getters
    public String getRoomId() { return roomId; }
    public String getType() { return type; }
    public int getCapacity() { return capacity; }
    public double getPricePerHour() { return pricePerHour; }
}
