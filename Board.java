import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {

    private static final Color[] TILE_COLORS = {
            new Color(0xffffe0), new Color(0xeee4da), new Color(0xede0c8), new Color(0xf2b179),
            new Color(0xf59563), new Color(0xf67c5f), new Color(0xf65e3b), new Color(0xedcf72),
            new Color(0xedcc61), new Color(0xedc850), new Color(0xedc53f), new Color(0xedc22e)
    };

    private static final Color EMPTY_TILE_COLOR = new Color(0xcdc1b4); // ค่า Value เป็น 0
    private static final Color FONT_COLOR = new Color(0x776e65);
    private static final Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 36);

    private int[][] values;

    public Board(Process process) {
        this.values = process.getValues();
        setLayout(new GridLayout(4, 4, 10, 10)); // กำหนดการจัดวางเป็น GridLayout 4x4 และระยะห่างระหว่างช่องเป็น 10 พิกเซล
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // เพิ่มขอบเขตว่างรอบ JPanel
        initializeTiles(); // เรียกเมทอดเพื่อสร้างและเรียงลำดับช่องในตาราง
    }

    private void initializeTiles() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                TilePanel tilePanel = new TilePanel(values[i][j]); // สร้าง TilePanel สำหรับแต่ละช่องและกำหนดค่าตาม values
                add(tilePanel); // เพิ่ม TilePanel เข้าไปใน JPanel
            }
        }
    }

    public void updateBoard(int[][] newValues) {
        this.values = newValues;
        removeAll(); // กำจัดช่องทั้งหมดออกจาก JPanel
        initializeTiles(); // สร้างและเรียงลำดับช่องใหม่
        revalidate(); // ให้ JPanel ทำการเรียกวิธี layout ใหม่
        repaint(); // วาดการเปลี่ยนแปลงลงบน JPanel
    }

    private class TilePanel extends JPanel {
        private int value;

        public TilePanel(int value) {
            this.value = value;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // เรียกใช้เมธอด paintComponent() ของคลาสแม่ (JPanel) เพื่อทำงานก่อนวาดเนื้อหาของคลาส TilePanel.
            int tileSize = getWidth(); // กำหนดขนาดของช่องเท่ากับความกว้างและความสูงของ JPanel
            g.setColor(value == 0 ? EMPTY_TILE_COLOR : TILE_COLORS[(int) (Math.log(value) / Math.log(2)) - 1]);
            g.fillRect(0, 0, tileSize, tileSize); // วาดสี่เหลี่ยมผืนผ้าที่มีขนาดเท่ากับ tileSize

            if (value != 0) {
                g.setColor(FONT_COLOR);
                g.setFont(FONT);
                String s = String.valueOf(value); // แปลงค่าของไทล์เป็นสตริง
                FontMetrics fm = g.getFontMetrics(); // คำนวณพิกเซลของตัวหนังสือ
                int x = (tileSize - fm.stringWidth(s)) / 2;
                int y = ((tileSize - fm.getHeight()) / 2) + fm.getAscent();
                g.drawString(s, x, y);
            }
        }
    }
}
