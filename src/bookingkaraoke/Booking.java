package bookingkaraoke;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;


public class Booking {
    private static final AtomicInteger idCounter = new AtomicInteger(0);
    private String bookingId;
    private String customerName;
    private String customerPhone;
    private Room room;
    private Date bookingDate;
    private int durationHours;

    public Booking(String customerName, String customerPhone, Room room, Date bookingDate, int durationHours) {
        this.bookingId = "BK-" + String.format("%04d", idCounter.incrementAndGet());
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.room = room;
        this.bookingDate = bookingDate;
        this.durationHours = durationHours;
    }

    // Getters
    public String getBookingId() { return bookingId; }
    public String getCustomerName() { return customerName; }
    public String getCustomerPhone() { return customerPhone; }
    public Room getRoom() { return room; }
    public Date getBookingDate() { return bookingDate; }
    public int getDurationHours() { return durationHours; }
    public double getTotalPrice() { return room.getPricePerHour() * durationHours; }

    // Setters
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }
    public void setRoom(Room room) { this.room = room; }
    public void setDurationHours(int durationHours) { this.durationHours = durationHours; }
}
