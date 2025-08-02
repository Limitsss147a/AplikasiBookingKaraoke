package bookingkaraoke;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JPanel menuPanel; // Jadikan menuPanel sebagai field agar bisa di-update
    private DataManager dataManager;
    private boolean isAdmin = false; // Variabel untuk melacak status login admin

    public MainFrame() {
        dataManager = new DataManager();

        setTitle("Sistem Booking Tempat Karaoke");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Panel Menu di sebelah kiri
        menuPanel = new JPanel();
        add(menuPanel, BorderLayout.WEST);

        // Panel Konten di sebelah kanan dengan CardLayout
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        // Menambahkan semua panel/view ke contentPanel
        setupContentPanels();
        
        // Membangun menu awal untuk user biasa
        updateMenuView();

        add(contentPanel, BorderLayout.CENTER);
    }

    private void setupContentPanels() {
        DaftarBookingPanel daftarBookingPanel = new DaftarBookingPanel(dataManager);
        
        contentPanel.add(createHomePanel(), "Home");
        contentPanel.add(new FormPemesananPanel(cardLayout, contentPanel, dataManager, daftarBookingPanel), "FormPemesanan");
        contentPanel.add(daftarBookingPanel, "DaftarBooking");
        // Ganti EditBatalBookingPanel menjadi KelolaBookingPanel
        contentPanel.add(new KelolaBookingPanel(dataManager, daftarBookingPanel), "KelolaBooking");
        contentPanel.add(new DaftarRuanganPanel(dataManager), "DaftarRuangan");
    }
    

    private void updateMenuView() {
        menuPanel.removeAll(); // Hapus semua komponen menu lama
        menuPanel.setBackground(new Color(45, 52, 54));
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setPreferredSize(new Dimension(220, 0));

        JLabel menuTitle = new JLabel("MENU PROGRAM");
        menuTitle.setForeground(Color.WHITE);
        menuTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        menuTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuTitle.setBorder(new EmptyBorder(20, 10, 20, 10));
        menuPanel.add(menuTitle);

        // Tentukan item menu berdasarkan status admin
        if (isAdmin) {
            // Menu untuk Admin
            addMenuButton("Home", "Home");
            addMenuButton("Form Pemesanan", "FormPemesanan");
            addMenuButton("Daftar Booking", "DaftarBooking");
            addMenuButton("Daftar Ruangan", "DaftarRuangan");
            
            // Menu khusus Admin
            addMenuButton("Kelola Booking", "KelolaBooking", new Color(255, 193, 7)); // Warna beda untuk menu admin
            
            // Tombol Logout
            JButton logoutButton = new JButton("Logout");
            configureMenuButton(logoutButton, new Color(220, 221, 225));
            logoutButton.addActionListener(e -> handleLogout());
            menuPanel.add(logoutButton);

        } else {
            // Menu untuk User Biasa
            addMenuButton("Home", "Home");
            addMenuButton("Form Pemesanan", "FormPemesanan");
            addMenuButton("Daftar Booking", "DaftarBooking");
            addMenuButton("Daftar Ruangan", "DaftarRuangan");

            // Tombol Login Admin
            JButton loginButton = new JButton("Login Admin");
            configureMenuButton(loginButton, new Color(255, 193, 7));
            loginButton.addActionListener(e -> handleAdminLogin());
            menuPanel.add(loginButton);
        }

        menuPanel.revalidate();
        menuPanel.repaint();
    }


    private void addMenuButton(String text, String cardName) {
        addMenuButton(text, cardName, new Color(220, 221, 225));
    }
    

    private void addMenuButton(String text, String cardName, Color color) {
        JButton button = new JButton(text);
        configureMenuButton(button, color);
        button.addActionListener(e -> cardLayout.show(contentPanel, cardName));
        menuPanel.add(button);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    }


    private void configureMenuButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        if (bgColor.equals(new Color(220, 53, 69)) || bgColor.equals(new Color(23, 162, 184))) {
            button.setForeground(Color.WHITE); // Teks putih untuk tombol berwarna
        }
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
    }

    private void handleAdminLogin() {
        JPasswordField passwordField = new JPasswordField(10);
        int option = JOptionPane.showConfirmDialog(this, passwordField, "Masukkan Password Admin", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            char[] password = passwordField.getPassword();
            // Password admin di-hardcode untuk contoh ini
            if (Arrays.equals(password, "admin123".toCharArray())) {
                isAdmin = true;
                updateMenuView(); // Bangun ulang menu untuk admin
                cardLayout.show(contentPanel, "Home"); // Pindah ke home setelah login
                JOptionPane.showMessageDialog(this, "Login sebagai admin berhasil!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Password salah!", "Gagal Login", JOptionPane.ERROR_MESSAGE);
            }
            // Kosongkan password dari memori
            Arrays.fill(password, '0');
        }
    }
    
 
    private void handleLogout() {
        isAdmin = false;
        updateMenuView(); // Bangun ulang menu untuk user biasa
        cardLayout.show(contentPanel, "Home"); // Kembali ke home
        JOptionPane.showMessageDialog(this, "Anda telah logout.", "Logout", JOptionPane.INFORMATION_MESSAGE);
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
