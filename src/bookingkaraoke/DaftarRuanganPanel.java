package bookingkaraoke;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DaftarRuanganPanel extends JPanel {
    public DaftarRuanganPanel(DataManager dataManager) {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(new Color(240, 240, 240));

        JLabel headerLabel = new JLabel("DAFTAR RUANGAN TERSEDIA", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(headerLabel, BorderLayout.NORTH);

        String[] columnNames = {"ID Ruangan", "Tipe", "Kapasitas", "Harga per Jam"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        for (Room room : dataManager.getRooms()) {
            Object[] row = {
                room.getRoomId(),
                room.getType(),
                room.getCapacity() + " orang",
                String.format("Rp %,.0f", room.getPricePerHour())
            };
            model.addRow(row);
        }

        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}
