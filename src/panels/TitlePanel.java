package panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Displays the title screen
 * Has two buttons: Start and Help
 */

public class TitlePanel extends JPanel {

    private final ParentPanel parentPanel;

    public TitlePanel(ParentPanel parentPanel) {
        this.parentPanel = parentPanel;

        setPreferredSize(new Dimension(700, 700));
        setLayout(new BorderLayout());

        setGraphics();
    }

    /**
     * Sets Graphics of the title panel
     */
    private void setGraphics() {
        JPanel centerPanel = new JPanel(new GridBagLayout());
        JPanel imagePanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 50));
        JLabel title = new JLabel();

        Color color = new Color(230, 69, 69);

        Border border = BorderFactory.createEmptyBorder(100, 0, 100, 0);
        Border border2 = BorderFactory.createEmptyBorder(0, 0, 300, 0);

        title.setIcon(getTitleImage());

        imagePanel.add(title);
        imagePanel.setBorder(border);
        imagePanel.setSize(new Dimension(150, 150));
        imagePanel.setBackground(color);

        buttonPanel.setPreferredSize(new Dimension(200, 150));
        buttonPanel.setBackground(color);

        createButton("START", buttonPanel);
        createButton("HELP", buttonPanel);

        centerPanel.setBorder(border2);
        centerPanel.setPreferredSize(new Dimension(150, 150));
        centerPanel.setBackground(color);
        centerPanel.add(buttonPanel);


        add(centerPanel, BorderLayout.CENTER);
        add(imagePanel, BorderLayout.NORTH);
    }

    /**
     * Creates the buttons of the title panel
     * @param text The text of the button
     * @param panel The JPanel where the buttons are put in
     */
    private void createButton(String text, JPanel panel) {
        JButton b = new JButton(text);
        CompoundBorder border = new CompoundBorder(BorderFactory.createLineBorder(new Color(125, 31, 31), 2), BorderFactory.createEmptyBorder(30, 20, 30, 20));
        b.setBackground(new Color(214, 40, 40));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(border);
        b.setAlignmentX(JButton.CENTER_ALIGNMENT);
        b.setAlignmentY(JButton.CENTER_ALIGNMENT);
        b.setFont(new Font("Gill Sans MT", Font.BOLD, 45));
        b.setMargin(new Insets(1, 1,1, 1));
        if (text.equalsIgnoreCase("start")) {
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    parentPanel.initializeMenuPanel();
                }
            });
        } else {
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    HelpPanel.showDialog();
                }
            });
        }

        panel.add(b);
    }

    /**
     * Gets the title image for the title panel
     * @return An ImageIcon of the title image
     */
    private ImageIcon getTitleImage() {
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(TitlePanel.class.getResource("/images/title.png")));
            Image tempImage = icon.getImage().getScaledInstance(700, (int)(icon.getIconHeight()/1.25), Image.SCALE_SMOOTH);
            return new ImageIcon(tempImage);
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
}