package bookingkaraoke;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;

public class DaftarBookingPanel extends JPanel {
    private DefaultTableModel tableModel;
    private JTable bookingTable;
    private DataManager dataManager;

    public DaftarBookingPanel(DataManager dataManager) {
        this.dataManager = dataManager;
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(new Color(240, 240, 240));

        JLabel headerLabel = new JLabel("DAFTAR SEMUA BOOKING", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(headerLabel, BorderLayout.NORTH);

        String[] columnNames = {"Kode Booking", "Nama Pemesan", "Telepon", "Ruangan", "Tanggal", "Jam Mulai", "Durasi (Jam)", "Total Biaya"};
        tableModel = new DefaultTableModel(columnNames, 0);
        bookingTable = new JTable(tableModel);
        bookingTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        bookingTable.setRowHeight(25);
        bookingTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        refreshTable();

        JScrollPane scrollPane = new JScrollPane(bookingTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        for (Booking booking : dataManager.getBookings()) {
            Object[] row = {
                booking.getBookingId(),
                booking.getCustomerName(),
                booking.getCustomerPhone(),
                booking.getRoom().getRoomId() + " (" + booking.getRoom().getType() + ")",
                dateFormat.format(booking.getBookingDate()),
                timeFormat.format(booking.getBookingDate()),
                booking.getDurationHours(),
                String.format("Rp %,.0f", booking.getTotalPrice())
            };
            tableModel.addRow(row);
        }
    }
}
