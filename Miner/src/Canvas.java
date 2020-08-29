import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int x = 0; x < Miner.FIELD_SIZE; x++)
            for (int y = 0; y < Miner.FIELD_SIZE; y++) Miner.field[y][x].paint(g, x, y);
    }
}
