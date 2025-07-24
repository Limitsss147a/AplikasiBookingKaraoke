package bookingkaraoke;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private DataManager dataManager;

    public MainFrame() {
        dataManager = new DataManager();

        setTitle("Sistem Booking Tempat Karaoke");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Panel Menu di sebelah kiri
        JPanel menuPanel = createMenuPanel();
        add(menuPanel, BorderLayout.WEST);

        // Panel Konten di sebelah kanan dengan CardLayout
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        // Menambahkan semua panel/view ke contentPanel
        DaftarBookingPanel daftarBookingPanel = new DaftarBookingPanel(dataManager);
        
        contentPanel.add(createHomePanel(), "Home");
        contentPanel.add(new FormPemesananPanel(cardLayout, contentPanel, dataManager, daftarBookingPanel), "FormPemesanan");
        contentPanel.add(daftarBookingPanel, "DaftarBooking");
        contentPanel.add(new EditBatalBookingPanel(dataManager, daftarBookingPanel), "EditBatalBooking");
        contentPanel.add(new DaftarRuanganPanel(dataManager), "DaftarRuangan");

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(new Color(45, 52, 54)); // Warna latar belakang menu (gelap)
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setPreferredSize(new Dimension(220, 0)); // Lebar menu diperbesar sedikit

        JLabel menuTitle = new JLabel("MENU PROGRAM");
        menuTitle.setForeground(Color.WHITE);
        menuTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        menuTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuTitle.setBorder(new EmptyBorder(20, 10, 20, 10));
        menuPanel.add(menuTitle);

        String[] menuItems = {"Home", "Form Pemesanan", "Daftar Booking", "Edit/Batal Booking", "Daftar Ruangan"};
        String[] cardNames = {"Home", "FormPemesanan", "DaftarBooking", "EditBatalBooking", "DaftarRuangan"};

        for (int i = 0; i < menuItems.length; i++) {
            String item = menuItems[i];
            String cardName = cardNames[i];
            JButton button = new JButton(item);
            
            // --- PERBAIKAN UI ---
            button.setBackground(new Color(220, 221, 225)); // Warna tombol terang
            button.setForeground(Color.BLACK); // Warna font hitam agar kontras
            button.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Font dibuat bold
            // --------------------

            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            button.setFocusPainted(false);
            button.setBorder(new EmptyBorder(10, 20, 10, 20));
            button.addActionListener(e -> cardLayout.show(contentPanel, cardName));
            
            menuPanel.add(button);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        return menuPanel;
    }

    private JPanel createHomePanel() {
        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.setBackground(Color.WHITE);

        JLabel welcomeLabel = new JLabel("Selamat Datang di Sistem Booking Karaoke", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(new Color(45, 52, 54));

        JLabel subLabel = new JLabel("Silakan pilih menu di sebelah kiri untuk memulai.", SwingConstants.CENTER);
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBackground(Color.WHITE);
        textPanel.add(welcomeLabel);
        textPanel.add(subLabel);

        homePanel.add(textPanel, BorderLayout.CENTER);
        return homePanel;
    }
}
