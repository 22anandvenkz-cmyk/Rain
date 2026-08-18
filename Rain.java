import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MatrixRain extends JPanel {
    private final int fontSize = 16;
    private final int[] yPositions;
    private final Random random = new Random();

    public MatrixRain(int width, int height) {
        setBackground(Color.RED);
        int columns = width / fontSize;
        yPositions = new int[columns];
        // Distribute the starting points randomly across the columns
        for (int i = 0; i < columns; i++) {
            yPositions[i] = random.nextInt(height / fontSize) * fontSize;
        }

        // Trigger screen updates every 50ms for smooth animations
        Timer timer = new Timer(50, e -> repaint());
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        // Translucent background overlay leaves trail shadows behind the letters
        g2.setColor(new Color(0, 0, 0, 25));
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setFont(new Font("Monospaced", Font.BOLD, fontSize));
        g2.setColor(Color.GREEN);

        for (int i = 0; i < yPositions.length; i++) {
            // Generate a random character (Katakana/English style mix)
            char text = (char) (random.nextInt(95) + 33);
            int x = i * fontSize;
            int y = yPositions[i];

            g2.drawString(String.valueOf(text), x, y);

            // If a drop hits the screen bottom, reset its height randomly
            if (y > getHeight() && random.nextInt(100) > 95) {
                yPositions[i] = 0;
            } else {
                yPositions[i] += fontSize;
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Matrix Code Rain");
        MatrixRain panel = new MatrixRain(800, 600);
        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
