package bookingkaraoke;

import java.util.ArrayList;
import java.util.Date;


public class DataManager {
    private ArrayList<Booking> bookings;
    private ArrayList<Room> rooms;

    public DataManager() {
        bookings = new ArrayList<>();
        rooms = new ArrayList<>();
        initializeRooms();
        initializeDummyBookings();
    }

    private void initializeRooms() {
        rooms.add(new Room("R01", "Small", 4, 50000));
        rooms.add(new Room("R02", "Small", 4, 50000));
        rooms.add(new Room("M01", "Medium", 8, 80000));
        rooms.add(new Room("M02", "Medium", 8, 80000));
        rooms.add(new Room("L01", "Large", 12, 120000));
        rooms.add(new Room("V01", "VIP", 15, 200000));
    }
    
    private void initializeDummyBookings() {
        Booking b1 = new Booking("Andi", "081234567890", findRoomById("M01"), new Date(), 2);
        Booking b2 = new Booking("Budi", "087712345678", findRoomById("L01"), new Date(), 3);
        bookings.add(b1);
        bookings.add(b2);
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public Booking findBookingById(String id) {
        for (Booking booking : bookings) {
            if (booking.getBookingId().equalsIgnoreCase(id)) {
                return booking;
            }
        }
        return null;
    }

    public void updateBooking(Booking updatedBooking) {
        Booking booking = findBookingById(updatedBooking.getBookingId());
        if (booking != null) {
            booking.setCustomerName(updatedBooking.getCustomerName());
            booking.setCustomerPhone(updatedBooking.getCustomerPhone());
            booking.setRoom(updatedBooking.getRoom());
            booking.setDurationHours(updatedBooking.getDurationHours());
        }
    }

    public void deleteBooking(String id) {
        bookings.removeIf(booking -> booking.getBookingId().equalsIgnoreCase(id));
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public Room findRoomById(String id) {
        for (Room room : rooms) {
            if (room.getRoomId().equalsIgnoreCase(id)) {
                return room;
            }
        }
        return null;
    }
}
