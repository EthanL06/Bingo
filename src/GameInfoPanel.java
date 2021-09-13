import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

public class GameInfoPanel extends JPanel {

    private final ParentPanel parentPanel;
    private final BingoParent bingoParent;
    private Color color;

    public GameInfoPanel(ParentPanel parentPanel, BingoParent bingoParent) {
        this.parentPanel = parentPanel;
        this.bingoParent = bingoParent;
        color = new Color(230, 69, 69);

        setGraphics();
        parentPanel.add(this, "game info");
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
    
    private void setGraphics() {
    	setLayout(new BorderLayout());
    	setBackground(color);

    	JPanel imagePanel = new JPanel(new GridBagLayout());
    	setImage(imagePanel);
    	
    	JPanel gameInfo = new JPanel(new GridBagLayout());
        setGameInfoPanel(gameInfo);

    	add(imagePanel, BorderLayout.NORTH);
    	add(gameInfo);
    }
    
    private void setImage(JPanel imagePanel) {
        ImageIcon gameInfoImage = null;
        Border border = BorderFactory.createEmptyBorder(100, 0, 100, 0);

        try {
            gameInfoImage = new ImageIcon(ImageIO.read(MenuPanel.class.getResource("/images/game_info_2.png")));
        } catch (Exception e) {
            System.out.println(e);
        }

        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(gameInfoImage);

        imagePanel.add(imageLabel);
        imagePanel.setBorder(border);
        imagePanel.setSize(new Dimension(gameInfoImage.getIconWidth(), gameInfoImage.getIconWidth()));
        imagePanel.setBackground(color);
    }

    private void setGameInfoPanel(JPanel gameInfo) {
        Border border = BorderFactory.createEmptyBorder(-100, 0, 0, 0);
        GridBagConstraints gc = new GridBagConstraints();
        Font font = new Font("Arial Rounded MT", Font.BOLD, 50);

        gameInfo.setBorder(border);
        gameInfo.setBackground(color);

        JLabel parameters = new JLabel("PARAMETERS");
        parameters.setFont(font);
        parameters.setForeground(Color.WHITE);

        gc.gridx = 0;
        gc.gridy = 0;
//        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets(2, 0, 0, 2);
//        gc.weightx = 1;
        gc.weighty = 1;

        gameInfo.add(parameters, gc);
    }
}