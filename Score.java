import javax.swing.*;
import java.awt.*;

public class Score extends JPanel {
    private JLabel scoreLabel;
    private int scoreValue;

    public Score() {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleScore = new JLabel("Score: ");
        titleScore.setFont(new Font("SansSerif", Font.BOLD, 32));
        add(titleScore);

        scoreValue = 0;
        scoreLabel = new JLabel(String.valueOf(scoreValue));
        scoreLabel.setFont(new Font("SansSerif", Font.PLAIN, 32));
        add(scoreLabel);
    }

    public void updateScore(int newScore) {
        scoreValue = newScore;
        scoreLabel.setText(String.valueOf(scoreValue));
    }

    public int getScore() {
        return scoreValue;
    }
}
