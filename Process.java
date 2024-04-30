import java.util.Random;

public class Process {
    private int[][] values = new int[4][4];
    private int score = 0;
    private Random random = new Random();
    private Game2048 game;

    public Process(Game2048 game) {
        this.game = game;
    }

    public void generateNewElement() {
        int emptyCells = 0;
        for (int[] row : values) {
            for (int cell : row) {
                if (cell == 0) {
                    emptyCells++;
                }
            }
        }
        if (emptyCells == 0) {
            return;
        }

        int newValue = (random.nextInt(2) + 1) * 2;
        int index = random.nextInt(emptyCells);
        int count = 0;
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                if (values[i][j] == 0) {
                    if (count == index) {
                        values[i][j] = newValue;
                        return;
                    }
                    count++;
                }
            }
        }
    }

    public void moveLeft() {
        for (int i = 0; i < values.length; i++) {
            boolean[] merged = new boolean[values[i].length]; // เก็บสถานะการรวมของแต่ละ Tile เพื่อป้องกันการรวม Tile ซ้ำซ้อน
            for (int j = 1; j < values[i].length; j++) { // เริ่มต้นตรวจสอบที่ตำแหน่งทางซ้ายสุดเพื่อไม่ต้องทำซ้ำกับคอลัมน์แรก
                if (values[i][j] != 0) { // ถ้า Tile ณ ตำแหน่งปัจจุบันไม่ว่าง
                    int current = j;
                    // ย้าย Tile ทางซ้ายไปที่ตำแหน่งสุดซ้ายของแถว
                    while (current > 0 && values[i][current - 1] == 0) {
                        values[i][current - 1] = values[i][current];
                        values[i][current] = 0;
                        current--;
                    }
                    // ตรวจสอบการรวม Tile ถ้าไม่เคยรวม Tile นี้ไปแล้ว และ Tile ถัดไปเหมือนกัน
                    if (current > 0 && !merged[current - 1] && values[i][current - 1] == values[i][current]) {
                        values[i][current - 1] *= 2; // รวมค่า Tile
                        score += values[i][current - 1]; // เพิ่มคะแนน
                        values[i][current] = 0; // ล้างค่า Tile ที่ถูกรวม
                        merged[current - 1] = true; // ตั้งสถานะว่า Tile ที่ถูกรวมไปแล้ว
                    }
                }
            }
        }
        generateNewElement(); // สร้าง Tile ใหม่
        checkWin(); // เช็คว่าชนะหรือยัง
        checkLose(); // เช็คว่าแพ้หรือยัง
    }

    public void moveRight() {
        for (int i = 0; i < values.length; i++) {
            boolean[] merged = new boolean[values[i].length];
            for (int j = values[i].length - 2; j >= 0; j--) {
                if (values[i][j] != 0) {
                    int current = j;
                    while (current < values[i].length - 1 && values[i][current + 1] == 0) {
                        values[i][current + 1] = values[i][current];
                        values[i][current] = 0;
                        current++;
                    }
                    if (current < values[i].length - 1 && !merged[current + 1] && values[i][current + 1] == values[i][current]) {
                        values[i][current + 1] *= 2;
                        score += values[i][current + 1]; 
                        values[i][current] = 0;
                        merged[current + 1] = true;
                    }
                }
            }
        }
        generateNewElement();
        checkWin();
        checkLose();
    }

    public void moveUp() {
        for (int j = 0; j < values[0].length; j++) {
            boolean[] merged = new boolean[values.length];
            for (int i = 1; i < values.length; i++) {
                if (values[i][j] != 0) {
                    int current = i;
                    while (current > 0 && values[current - 1][j] == 0) {
                        values[current - 1][j] = values[current][j];
                        values[current][j] = 0;
                        current--;
                    }
                    if (current > 0 && !merged[current - 1] && values[current - 1][j] == values[current][j]) {
                        values[current - 1][j] *= 2;
                        score += values[current - 1][j];
                        values[current][j] = 0;
                        merged[current - 1] = true;
                    }
                }
            }
        }
        generateNewElement();
        checkWin();
        checkLose();
    }

    public void moveDown() {
        for (int j = 0; j < values[0].length; j++) {
            boolean[] merged = new boolean[values.length];
            for (int i = values.length - 2; i >= 0; i--) {
                if (values[i][j] != 0) {
                    int current = i;
                    while (current < values.length - 1 && values[current + 1][j] == 0) {
                        values[current + 1][j] = values[current][j];
                        values[current][j] = 0;
                        current++;
                    }
                    if (current < values.length - 1 && !merged[current + 1] && values[current + 1][j] == values[current][j]) {
                        values[current + 1][j] *= 2;
                        score += values[current + 1][j];
                        values[current][j] = 0;
                        merged[current + 1] = true;
                    }
                }
            }
        }
        generateNewElement();
        checkWin();
        checkLose();
    }

    public int[][] getValues() {
        return values;
    }

    public int getScore() { // รับคะแนน
        return score;
    }

    public void resetScore() { // รีคะแนน
        score = 0;
    }

    private void checkWin() {
        for (int[] row : values) { // วนลูปผ่านแถวของ values (บอร์ด)
            for (int cell : row) { // วนลูปผ่านค่าในแถวนั้น ๆ (เซลล์)
                if (cell == 2048) {
                    win();
                    return;
                }
            }
        }
    }

    private void checkLose() {
        boolean movable = false; // กำหนดให้การเคลื่อนที่ของ Tiles ในตารางเป็น false เพื่อบอกว่ายังไม่พบการเคลื่อนที่ที่เป็นไปได้
        outerloop: // ใช้ในการระบุลูปภายนอก เพื่อใช้ในการข้ามการวนลูปภายใน
        for (int i = 0; i < values.length; i++) { // วนลูปสำหรับการค้นหาในแนวนอนของตาราง
            for (int j = 0; j < values[i].length; j++) { // วนลูปสำหรับการค้นหาในแนวตั้งของตาราง
                int current = values[i][j]; // กำหนดค่าปัจจุบันในตารางที่ตำแหน่ง [i][j] ให้กับตัวแปร current
                if (current == 0) { // ตรวจสอบว่าค่าในตำแหน่งปัจจุบันเป็น 0 หรือไม่ (หมายถึงยังมีช่องว่างในตาราง)
                    movable = true; // กำหนด movable เป็น true เพื่อบ่งบอกว่ายังมีการเคลื่อนที่ได้
                    break outerloop; // จบการทำงานของลูปภายใน
                }
                if (j < values[i].length - 1 && values[i][j + 1] == current) { // ตรวจสอบว่ามี Tile ที่มีค่าเท่ากับ current ในแถวหรือไม่
                    movable = true;
                    break outerloop;
                }
                if (i < values.length - 1 && values[i + 1][j] == current) { // ตรวจสอบว่ามี Tile ที่มีค่าเท่ากับ current ในคอลัมน์หรือไม่
                    movable = true;
                    break outerloop;
                }
            }
        }
        // รันต่อจากการจบการทำงานของลูปภายใน
        if (!movable) { // ถ้าไม่สามารถเคลื่อนที่ต่อได้
            lose();
        }
    }

    public void win() {
        game.win();
    }

    public void lose() {
        game.lose();
    }

    public void resetGame() {
        values = new int[4][4];
        generateNewElement();
        generateNewElement();
    }
}