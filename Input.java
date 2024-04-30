import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {
    private Game2048 game;
    private boolean resetKeyPressed = false;

    public Input(Game2048 game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // ไม่ต้องทำอะไร?
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (resetKeyPressed && e.getKeyCode() == KeyEvent.VK_R) { // ตรวจสอบว่าปุ่ม R ถูกปล่อยหลังจากกด
            resetKeyPressed = false; // กำหนดให้ปุ่ม R ไม่ได้ถูกกดอีก
            game.getProcess().resetScore(); // รีเซ็ตคะแนน
            game.resetGame(); // รีเซ็ตเกม
        } else {
            char keyChar = Character.toLowerCase(e.getKeyChar());
            switch (keyChar) {
                case 'a':
                    game.getProcess().moveLeft();
                    break;
                case 'd':
                    game.getProcess().moveRight();
                    break;
                case 'w':
                    game.getProcess().moveUp();
                    break;
                case 's':
                    game.getProcess().moveDown();
                    break;
            }
            game.UpdateGUI(); // อัปเดต GUI หลังจากการกดปุ่ม
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_R) {
            resetKeyPressed = true; // เมื่อกดปุ่ม R ให้กำหนดว่าปุ่ม R ถูกกด
        } else {
            game.UpdateGUI();
        }
    }
}
