package bookingkaraoke;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class KelolaBookingPanel extends JPanel {
    private JTextField searchField, namaField, telpField, durasiField;
    private JComboBox<String> ruanganComboBox;
    private JLabel bookingIdLabel;
    private JButton updateButton, deleteButton;
    private DataManager dataManager;
    private DaftarBookingPanel daftarBookingPanel;
    private Booking currentBooking;

    public KelolaBookingPanel(DataManager dataManager, DaftarBookingPanel daftarBookingPanel) {
        this.dataManager = dataManager;
        this.daftarBookingPanel = daftarBookingPanel;
        
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(new Color(240, 240, 240));

        // Panel Pencarian
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Masukkan Kode Booking:"));
        searchField = new JTextField(15);
        searchPanel.add(searchField);
        JButton searchButton = new JButton("Cari");
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);
        
        // Panel Form Edit
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Detail Booking"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Kode Booking:"), gbc);
        gbc.gridx = 1; bookingIdLabel = new JLabel("-"); formPanel.add(bookingIdLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Nama Pemesan:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; namaField = new JTextField(); formPanel.add(namaField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("Nomor Telepon:"), gbc);
        gbc.gridx = 1; telpField = new JTextField(); formPanel.add(telpField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(new JLabel("Ruangan:"), gbc);
        gbc.gridx = 1; ruanganComboBox = new JComboBox<>(); formPanel.add(ruanganComboBox, gbc);
        for (Room room : dataManager.getRooms()) {
            ruanganComboBox.addItem(room.getRoomId() + " - " + room.getType());
        }

        gbc.gridx = 0; gbc.gridy = 4; formPanel.add(new JLabel("Durasi (Jam):"), gbc);
        gbc.gridx = 1; durasiField = new JTextField(); formPanel.add(durasiField, gbc);
        
        add(formPanel, BorderLayout.CENTER);

        // Panel Tombol Aksi
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        updateButton = new JButton("Update");
        updateButton.setBackground(new Color(52, 152, 219));
        updateButton.setForeground(Color.WHITE);
        
        deleteButton = new JButton("Batalkan Booking");
        deleteButton.setBackground(new Color(231, 76, 60));
        deleteButton.setForeground(Color.WHITE);
        
        actionPanel.add(updateButton);
        actionPanel.add(deleteButton);
        add(actionPanel, BorderLayout.SOUTH);
        
        setFieldsEnabled(false);

        searchButton.addActionListener(e -> searchBooking());
        updateButton.addActionListener(e -> updateBooking());
        deleteButton.addActionListener(e -> deleteBooking());
    }
    
    private void searchBooking() {
        String bookingId = searchField.getText();
        currentBooking = dataManager.findBookingById(bookingId);
        
        if (currentBooking != null) {
            bookingIdLabel.setText(currentBooking.getBookingId());
            namaField.setText(currentBooking.getCustomerName());
            telpField.setText(currentBooking.getCustomerPhone());
            durasiField.setText(String.valueOf(currentBooking.getDurationHours()));
            String roomIdentifier = currentBooking.getRoom().getRoomId() + " - " + currentBooking.getRoom().getType();
            ruanganComboBox.setSelectedItem(roomIdentifier);
            setFieldsEnabled(true);
        } else {
            JOptionPane.showMessageDialog(this, "Booking dengan kode '" + bookingId + "' tidak ditemukan.", "Tidak Ditemukan", JOptionPane.WARNING_MESSAGE);
            resetFields();
            setFieldsEnabled(false);
        }
    }

    private void updateBooking() {
        try {
            currentBooking.setCustomerName(namaField.getText());
            currentBooking.setCustomerPhone(telpField.getText());
            currentBooking.setDurationHours(Integer.parseInt(durasiField.getText()));
            String selectedRoomItem = (String) ruanganComboBox.getSelectedItem();
            String roomId = selectedRoomItem.split(" - ")[0];
            Room newRoom = dataManager.findRoomById(roomId);
            currentBooking.setRoom(newRoom);
            dataManager.updateBooking(currentBooking);
            daftarBookingPanel.refreshTable();
            JOptionPane.showMessageDialog(this, "Data booking berhasil diupdate.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            resetFields();
            setFieldsEnabled(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal mengupdate data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteBooking() {
        int choice = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin membatalkan booking ini?", "Konfirmasi Pembatalan", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            dataManager.deleteBooking(currentBooking.getBookingId());
            daftarBookingPanel.refreshTable();
            JOptionPane.showMessageDialog(this, "Booking berhasil dibatalkan.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            resetFields();
            setFieldsEnabled(false);
        }
    }

    private void setFieldsEnabled(boolean enabled) {
        namaField.setEnabled(enabled);
        telpField.setEnabled(enabled);
        durasiField.setEnabled(enabled);
        ruanganComboBox.setEnabled(enabled);
        updateButton.setEnabled(enabled);
        deleteButton.setEnabled(enabled);
    }
    
    private void resetFields() {
        searchField.setText("");
        bookingIdLabel.setText("-");
        namaField.setText("");
        telpField.setText("");
        durasiField.setText("");
        ruanganComboBox.setSelectedIndex(0);
        currentBooking = null;
    }
}
