import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Miner extends JFrame {

    final String TITLE_OF_GAME = "Miner";
    static final String SING_OF_FLAG = "F";
    static final int BLOCK_SIZE = 30;
    static final int FIELD_SIZE = 9;
    final int FIELD_DX = 6;
    final int FIELD_DY = 28 + 17;
    final int START_LOCATION = 200;
    final int MOUSE_BUTTON_LEFT = 1;
    final int MOUSE_BUTTON_RIGHT = 3;
    final int NUMBER_OF_MINES = 10;
    static final int [] COLOR_OF_NUMBERS = {0x0000FF, 0x008000, 0xFF0000, 0x800000, 0x0};
    static Cell [][] field = new Cell [FIELD_SIZE][FIELD_SIZE];
    Random random = new Random();
    static int countOpenedCell;
    static boolean youWin, bangMine;
    int bangX, bangY;

    public static void main(String[] args) {
        new Miner();
    }

    Miner () {
        setTitle(TITLE_OF_GAME);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(START_LOCATION, START_LOCATION,
                FIELD_SIZE*BLOCK_SIZE+FIELD_DX, FIELD_SIZE*BLOCK_SIZE+FIELD_DY);
        setResizable(false);
        TimerLabel timerLabel = new TimerLabel();
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Canvas canvas = new Canvas();
        canvas.setBackground(Color.WHITE);
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                int x = e.getX()/BLOCK_SIZE;
                int y = e.getY()/BLOCK_SIZE;
                if (e.getButton() == MOUSE_BUTTON_LEFT && !bangMine && !youWin)
                    if (field[y][x].isNotOpen()) {
                        openCells(x, y);
                        youWin = countOpenedCell == FIELD_SIZE*FIELD_SIZE - NUMBER_OF_MINES;
                        if (bangMine) {
                            bangX = x;
                            bangY = y;
                        }
                    }
                if (e.getButton() == MOUSE_BUTTON_RIGHT) field[y][x].inverseFlag();
                if (bangMine || youWin) timerLabel.stopTimer();
                canvas.repaint();
            }
        });
        add(BorderLayout.CENTER, canvas);
        add(BorderLayout.SOUTH, timerLabel);
        setVisible(true);
        initField();
    }

    void initField () {
        int x, y, countMines = 0;

        for (x = 0; x < FIELD_SIZE; x++) {
            for (y = 0; y < FIELD_SIZE; y++) {field[y][x] = new Cell();}
        }

        while (countMines < NUMBER_OF_MINES) {
            do {
                x = random.nextInt(FIELD_SIZE);
                y = random.nextInt(FIELD_SIZE);
            } while (field[y][x].isMined());
            field[y][x].mine();
            countMines++;
        }

        for (x = 0; x < FIELD_SIZE; x++) {
            for (y = 0; y < FIELD_SIZE; y++) {
                if (!field[y][x].isMined()) {
                    int count = 0;
                    for (int dx = -1; dx < 2; dx++) {
                        for (int dy = -1; dy < 2; dy++) {
                            int nx = x + dx;
                            int ny = y + dy;
                            if (nx < 0 || ny <0 || nx > FIELD_SIZE-1 || ny >FIELD_SIZE-1) {
                                nx = x;
                                ny = y;
                            }
                            count += (field[ny][nx].isMined()) ? 1 : 0;
                        }
                        field[y][x].setCountBomb(count);
                    }
                }
            }
        }
    }

    void openCells(int x, int y) {
        if (x < 0 || x > FIELD_SIZE-1 || y < 0 || y > FIELD_SIZE-1) return;
        if (!field[y][x].isNotOpen()) return;
        field[y][x].open();
        if (field[y][x].getCountBomb() > 0 || bangMine) return;
        for (int dx = -1; dx < 2; dx++)
            for (int dy = -1; dy < 2; dy++) openCells(x + dx, y + dy);
    }
}
