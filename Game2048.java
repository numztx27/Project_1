import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game2048 extends JFrame {

    private static final String TITLE = "2048";
    private static final String WIN_MSG = "You Win!!!.";
    private static final String LOSE_MSG = "Game Over!!!.";
    private static final String AGAiN_MSG = "Press 'Play Again' to Reset the game.";
    private JPanel statusBarPanel;
    private JLabel statusBar;
    private Process process;
    private Board board;
    private Score score;
    private MainMenu mainMenu;

    public Game2048(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        setTitle(TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 800);
        setResizable(false);
        setLocationRelativeTo(null);

        // Main Menu
        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToMainMenu();
            }
        });
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButtonPanel.add(backButton);
        add(backButtonPanel, BorderLayout.PAGE_END);

        process = new Process(this);
        // เริ่มเกมสุ่ม 2 ตัว เลยเขียน 2 ครั้ง
        process.generateNewElement();
        process.generateNewElement();

        board = new Board(process); // เปลี่ยน GUI เป็น Board
        add(board, BorderLayout.CENTER); // เปลี่ยนการเพิ่ม GUI เป็นการเพิ่ม board

        statusBarPanel = new JPanel(new GridBagLayout()); // ใช้ GridBagLayout เพื่อจัดวางตรงกลางของหน้าต่าง
        statusBar = new JLabel("Press 'R' to Reset And Play Again.");
        statusBar.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
        statusBarPanel.add(statusBar); // เพิ่ม statusBar ลงใน statusBarPanel
        // add(statusBarPanel, BorderLayout.PAGE_END); // เพิ่ม statusBarPanel ไว้ตอนจบหน้ากระดาษ

        score = new Score(); // สร้าง Score และส่งคะแนนเข้าไป
        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // สร้าง JPanel ใหม่และใช้ FlowLayout ให้
        scorePanel.add(score); // เพิ่ม Score ลงใน JPanel
        add(scorePanel, BorderLayout.NORTH); // ก็จะเพิ่มลงใน JPanel ที่สร้างไว้ด้านขวา

        Input input = new Input(this);
        addKeyListener(input);
        setFocusable(true);
    }

    private void backToMainMenu() {
        mainMenu.setVisible(true); // แสดงหน้าต่าง MainMenu
        dispose(); // ปิดหน้าต่าง Game2048
    }

    void UpdateGUI() {
        board.updateBoard(process.getValues()); // อัพเดทบอร์ด
        score.updateScore(process.getScore()); // อัพเดทคะแนน
        requestFocusInWindow();
    }

    void resetGame() {
        process.resetGame();
        board.updateBoard(process.getValues());
        score.updateScore(process.getScore()); // รีเซ็ตคะแนน
        statusBar.setText("Press 'R' to Reset And Play Again.");
        statusBar.setFont(new Font("SANS_SERIF", Font.BOLD, 24));
        statusBar.setForeground(Color.BLACK);
    }

    void win() {
        int currentScore = process.getScore(); // เก็บค่าคะแนนปัจจุบัน
        statusBar.setText(WIN_MSG + " " + "\nYour Score: " + " " + currentScore + "." + "\n" + " " + AGAiN_MSG);
        Color darkGreen = new Color(0, 128, 0);
        statusBar.setForeground(darkGreen);
        statusBar.setFont(new Font("SANS_SERIF", Font.BOLD, 24));
        UpdateGUI();
        int option = JOptionPane.showOptionDialog(this, statusBar.getText(), "Congratulations!",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "Play Again", "Quit" }, "default");
        if (option == 0) {
            getProcess().resetScore(); // รีเซ็ตคะแนน
            startNewGame();
        } else {
            System.exit(0);
        }
    }

    void lose() {
        int currentScore = process.getScore();
        statusBar.setText(LOSE_MSG + " " + "\nYour Score: " + " " + currentScore + "." + "\n" + " " + AGAiN_MSG);
        statusBar.setForeground(Color.RED);
        statusBar.setFont(new Font("SANS_SERIF", Font.BOLD, 24));
        UpdateGUI();
        int option = JOptionPane.showOptionDialog(this, statusBar.getText(), "Loser.", JOptionPane.PLAIN_MESSAGE,
                JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "Play Again", "Quit" }, "default");
        if (option == 0) {
            getProcess().resetScore(); // รีเซ็ตคะแนน
            startNewGame();
        } else {
            System.exit(0);
        }
    }

    void startNewGame() {
        resetGame();
        UpdateGUI();
    }

    public Process getProcess() {
        return process;
    }

}
