package bookingkaraoke;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class DataManager {

    private Connection connection;

    public DataManager() {
        // Mendapatkan koneksi dari kelas DatabaseConnection
        this.connection = DatabaseConnection.getConnection();
        // initializeRoomsFromDB(); // Anda bisa memanggil ini jika ingin memastikan ruangan ada
    }

    // Mengambil semua data ruangan dari database
    public ArrayList<Room> getRooms() {
        ArrayList<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM rooms";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                rooms.add(new Room(
                        rs.getString("roomId"),
                        rs.getString("type"),
                        rs.getInt("capacity"),
                        rs.getDouble("pricePerHour")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }
    
    // Mencari ruangan berdasarkan ID dari database
    public Room findRoomById(String id) {
        String query = "SELECT * FROM rooms WHERE roomId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Room(
                            rs.getString("roomId"),
                            rs.getString("type"),
                            rs.getInt("capacity"),
                            rs.getDouble("pricePerHour"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Menambahkan booking baru ke database
    public void addBooking(Booking booking) {
        String query = "INSERT INTO bookings (bookingId, customerName, customerPhone, roomId, bookingDate, durationHours) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, booking.getBookingId());
            pstmt.setString(2, booking.getCustomerName());
            pstmt.setString(3, booking.getCustomerPhone());
            pstmt.setString(4, booking.getRoom().getRoomId());
            // Konversi java.util.Date ke java.sql.Timestamp
            pstmt.setTimestamp(5, new java.sql.Timestamp(booking.getBookingDate().getTime()));
            pstmt.setInt(6, booking.getDurationHours());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mengambil semua data booking dari database
    public ArrayList<Booking> getBookings() {
        ArrayList<Booking> bookings = new ArrayList<>();
        // Query dengan JOIN untuk mendapatkan detail ruangan sekaligus
        String query = "SELECT b.*, r.type, r.capacity, r.pricePerHour FROM bookings b JOIN rooms r ON b.roomId = r.roomId";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Room room = new Room(
                        rs.getString("roomId"),
                        rs.getString("type"),
                        rs.getInt("capacity"),
                        rs.getDouble("pricePerHour"));
                
                // Buat objek booking dari hasil query
                // Perlu constructor yang bisa menerima semua parameter ini atau setter
                 Booking booking = new Booking(
                    rs.getString("customerName"),
                    rs.getString("customerPhone"),
                    room,
                    new Date(rs.getTimestamp("bookingDate").getTime()),
                    rs.getInt("durationHours")
                );
                // Karena bookingId dibuat otomatis, kita perlu set manual dari hasil DB
                // Anda perlu menambahkan setter untuk bookingId di kelas Booking.java
                // booking.setBookingId(rs.getString("bookingId")); 
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
    
    // Fungsi lainnya (findBookingById, updateBooking, deleteBooking) juga perlu diubah
    // untuk berinteraksi dengan database menggunakan query SQL (UPDATE, DELETE, SELECT WHERE).
    // Contoh di bawah:

    public void deleteBooking(String id) {
        String query = "DELETE FROM bookings WHERE bookingId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Booking findBookingById(String bookingId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void updateBooking(Booking currentBooking) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}