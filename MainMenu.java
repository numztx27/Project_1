import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    private static final String TITLE = "2048";
    private static final String START_GAME_TEXT = "Start Game";
    private static final String HOW_TO_PLAY_TEXT = "How to Play";
    private static final String QUIT_TEXT = "Quit";
    private static final String MainMenu_IMAGE_PATH = "1.png";
    private static final String HOW_TO_PLAY_IMAGE_PATH = "2.png";
    private JPanel mainMenuPanel;
    private JPanel howToPlayPanel;

    public MainMenu() {
        setTitle(TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setResizable(false);

        mainMenuPanel = createMainMenuPanel();
        howToPlayPanel = createHowToPlayPanel();

        switchToMainMenu();
        add(mainMenuPanel);
    }

    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImageIcon = new ImageIcon(MainMenu_IMAGE_PATH);
                Image backgroundImage = backgroundImageIcon.getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(15, 15, 15, 15);

        JButton startGameButton = createButton(START_GAME_TEXT);
        startGameButton.addActionListener(e -> startGame());
        panel.add(startGameButton, gbc);

        JButton howToPlayButton = createButton(HOW_TO_PLAY_TEXT);
        howToPlayButton.addActionListener(e -> showHowToPlay());
        gbc.gridy++;
        panel.add(howToPlayButton, gbc);

        JButton quitButton = createButton(QUIT_TEXT);
        quitButton.addActionListener(e -> quitGame());
        gbc.gridy++;
        panel.add(quitButton, gbc);

        return panel;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 50));
        button.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
        return button;
    }

    private JPanel createHowToPlayPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel imageLabel = new JLabel(new ImageIcon(HOW_TO_PLAY_IMAGE_PATH));
        panel.add(imageLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> switchToMainMenu());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void switchToMainMenu() {
        getContentPane().removeAll();
        getContentPane().add(mainMenuPanel);
        revalidate();
        repaint();
    }

    private void switchToHowToPlay() {
        getContentPane().removeAll();
        getContentPane().add(howToPlayPanel);
        revalidate();
        repaint();
    }

    private void startGame() {
        new Game2048(this).setVisible(true); // ส่ง MainMenu ไปให้ Game2048
        setVisible(false); // ซ่อนหน้าต่าง MainMenu
    }

    private void showHowToPlay() {
        switchToHowToPlay();
    }

    private void quitGame() {
        System.exit(0);
    }

}