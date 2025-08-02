package bookingkaraoke;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/booking_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Daftarkan driver JDBC
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Buat koneksi
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                // Tampilkan pesan error jika koneksi gagal
                javax.swing.JOptionPane.showMessageDialog(null, "Tidak dapat terhubung ke database: " + e.getMessage(), "Database Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                System.exit(0); // Keluar dari aplikasi jika tidak bisa konek
            }
        }
        return connection;
    }
}