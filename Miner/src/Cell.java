import java.awt.*;

public class Cell {

    boolean isOpen, isMine, isFlag;
    int countBombNear;

    void open() {
        isOpen = true;
        Miner.bangMine = isMine;
        if (!isMine) Miner.countOpenedCell++;
    }

    void inverseFlag() { isFlag = !isFlag; }

    boolean isNotOpen() { return !isOpen; }

    boolean isMined() { return isMine; }

    void mine() { isMine = true; }

    void setCountBomb(int count) { countBombNear = count; }

    int getCountBomb () { return countBombNear; }

    void paint(Graphics g, int x, int y) {
        g.setColor(Color.lightGray);
        g.drawRect(x* Miner.BLOCK_SIZE, y* Miner.BLOCK_SIZE, Miner.BLOCK_SIZE, Miner.BLOCK_SIZE);
        if (!isOpen) {
            if ((Miner.bangMine || Miner.youWin) && isMine) paintBomb(g, x, y, Color.BLACK);
            else {
                g.setColor(Color.lightGray);
                g.fill3DRect(x*Miner.BLOCK_SIZE,y*Miner.BLOCK_SIZE, Miner.BLOCK_SIZE, Miner.BLOCK_SIZE,true);
                if (isFlag) paintString(g, Miner.SING_OF_FLAG, x, y, Color.red);
            }
        } else
            if (isMine) paintBomb(g, x, y, Miner.bangMine ? Color.red : Color.black);
            else
                if (countBombNear > 0) paintString(g, Integer.toString(countBombNear), x, y,
                        new Color (Miner.COLOR_OF_NUMBERS[countBombNear-1]));
    }

    void paintBomb (Graphics g, int x, int y, Color color) {
        g.setColor(color);
        g.fillRect(x*Miner.BLOCK_SIZE+7, y*Miner.BLOCK_SIZE+10, 18, 10);
        g.fillRect(x*Miner.BLOCK_SIZE+11, y*Miner.BLOCK_SIZE+6, 10, 18);
        g.fillRect(x*Miner.BLOCK_SIZE+9, y*Miner.BLOCK_SIZE+8, 14, 14);
        g.setColor(Color.WHITE);
        g.fillRect(x*Miner.BLOCK_SIZE+11, y*Miner.BLOCK_SIZE+10, 4, 4);
    }

    void paintString (Graphics g, String str, int x, int y, Color color) {
        g.setColor(color);
        g.setFont(new Font("", Font.BOLD, Miner.BLOCK_SIZE));
        g.drawString(str, x*Miner.BLOCK_SIZE+8, y*Miner.BLOCK_SIZE+26);
    }
}
