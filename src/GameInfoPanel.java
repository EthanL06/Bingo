import javax.swing.*;
import java.awt.*;

public class GameInfoPanel extends JPanel {

    private final ParentPanel parentPanel;
    private final BingoParent bingoParent;

    public GameInfoPanel(ParentPanel parentPanel, BingoParent bingoParent) {
        this.parentPanel = parentPanel;
        this.bingoParent = bingoParent;

        parentPanel.add(this, "game info");
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
