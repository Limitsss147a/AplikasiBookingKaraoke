package bookingkaraoke;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;


public class KaraokeBookingSystem {
    public static void main(String[] args) {
        // Menjalankan GUI di Event Dispatch Thread (EDT) untuk keamanan thread Swing
        SwingUtilities.invokeLater(() -> {
            try {
                // Menggunakan Look and Feel sistem untuk tampilan yang lebih modern
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new MainFrame().setVisible(true);
        });
    }
}
