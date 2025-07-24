package bookingkaraoke;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class FormPemesananPanel extends JPanel {
    private JTextField namaField, telpField, durasiField;
    private JComboBox<String> ruanganComboBox;
    private DataManager dataManager;
    private DaftarBookingPanel daftarBookingPanel;
    private CardLayout cardLayout;
    private JPanel contentPanel;

    public FormPemesananPanel(CardLayout cardLayout, JPanel contentPanel, DataManager dataManager, DaftarBookingPanel daftarBookingPanel) {
        this.cardLayout = cardLayout;
        this.contentPanel = contentPanel;
        this.dataManager = dataManager;
        this.daftarBookingPanel = daftarBookingPanel;

        setLayout(new GridBagLayout());
        setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel formWrapper = new JPanel(new BorderLayout(0, 15));
        formWrapper.setBorder(BorderFactory.createLineBorder(new Color(223, 223, 223)));
        formWrapper.setBackground(Color.WHITE);

        JLabel headerLabel = new JLabel("FORM INPUT PEMESANAN", SwingConstants.CENTER);
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(46, 204, 113));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerLabel.setPreferredSize(new Dimension(0, 60));
        formWrapper.add(headerLabel, BorderLayout.NORTH);

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(Color.WHITE);
        fieldsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints fgbc = new GridBagConstraints();
        fgbc.insets = new Insets(8, 8, 8, 8);
        fgbc.anchor = GridBagConstraints.WEST;
        fgbc.fill = GridBagConstraints.HORIZONTAL;

        fgbc.gridx = 0; fgbc.gridy = 0;
        fieldsPanel.add(new JLabel("NAMA PEMESAN:"), fgbc);
        fgbc.gridx = 1; fgbc.weightx = 1.0;
        namaField = new JTextField(20);
        fieldsPanel.add(namaField, fgbc);

        fgbc.gridx = 0; fgbc.gridy = 1; fgbc.weightx = 0;
        fieldsPanel.add(new JLabel("NOMOR TELEPON:"), fgbc);
        fgbc.gridx = 1; fgbc.weightx = 1.0;
        telpField = new JTextField(20);
        fieldsPanel.add(telpField, fgbc);
        
        fgbc.gridx = 0; fgbc.gridy = 2; fgbc.weightx = 0;
        fieldsPanel.add(new JLabel("PILIH RUANGAN:"), fgbc);
        fgbc.gridx = 1; fgbc.weightx = 1.0;
        ruanganComboBox = new JComboBox<>();
        for (Room room : dataManager.getRooms()) {
            ruanganComboBox.addItem(room.getRoomId() + " - " + room.getType());
        }
        fieldsPanel.add(ruanganComboBox, fgbc);

        fgbc.gridx = 0; fgbc.gridy = 3; fgbc.weightx = 0;
        fieldsPanel.add(new JLabel("DURASI (JAM):"), fgbc);
        fgbc.gridx = 1; fgbc.weightx = 1.0;
        durasiField = new JTextField(20);
        fieldsPanel.add(durasiField, fgbc);

        formWrapper.add(fieldsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(new EmptyBorder(0, 20, 20, 20));
        JButton saveButton = new JButton("Save");
        saveButton.setBackground(new Color(46, 204, 113));
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(new SaveAction());

        JButton resetButton = new JButton("Reset");
        resetButton.setBackground(new Color(231, 76, 60));
        resetButton.setForeground(Color.WHITE);
        resetButton.addActionListener(e -> resetForm());

        buttonPanel.add(saveButton);
        buttonPanel.add(resetButton);
        formWrapper.add(buttonPanel, BorderLayout.SOUTH);

        add(formWrapper, gbc);
    }

    private void resetForm() {
        namaField.setText("");
        telpField.setText("");
        durasiField.setText("");
        ruanganComboBox.setSelectedIndex(0);
    }
    
    private class SaveAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (namaField.getText().isEmpty() || telpField.getText().isEmpty() || durasiField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(FormPemesananPanel.this, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                String nama = namaField.getText();
                String telp = telpField.getText();
                int durasi = Integer.parseInt(durasiField.getText());
                if (durasi <= 0) {
                    JOptionPane.showMessageDialog(FormPemesananPanel.this, "Durasi harus lebih dari 0!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String selectedRoomItem = (String) ruanganComboBox.getSelectedItem();
                String roomId = selectedRoomItem.split(" - ")[0];
                Room selectedRoom = dataManager.findRoomById(roomId);

                if (selectedRoom != null) {
                    Booking newBooking = new Booking(nama, telp, selectedRoom, new Date(), durasi);
                    dataManager.addBooking(newBooking);
                    JOptionPane.showMessageDialog(FormPemesananPanel.this, "Booking berhasil disimpan!\nKode Booking: " + newBooking.getBookingId(), "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    daftarBookingPanel.refreshTable();
                    cardLayout.show(contentPanel, "DaftarBooking");
                    resetForm();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(FormPemesananPanel.this, "Durasi harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

