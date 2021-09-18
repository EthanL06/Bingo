package panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TitlePanel extends JPanel {

    /*
    Class that displays the title screen of the application
    Has two buttons: Start and Help
     */

    private final ParentPanel parentPanel;

    public TitlePanel(ParentPanel parentPanel) {
        this.parentPanel = parentPanel;

        setPreferredSize(new Dimension(1000, 1000));
        setLayout(new BorderLayout());

        setGraphics();
    }

    private void setGraphics() {
        JPanel centerPanel = new JPanel(new GridBagLayout());
        JPanel imagePanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 50));
        JLabel title = new JLabel();

        Color color = new Color(230, 69, 69);

        Border border = BorderFactory.createEmptyBorder(200, 0, 100, 0);
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

    private void createButton(String text, JPanel panel) {
        JButton b = new JButton(text);
        b.setBackground(new Color(214, 40, 40));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(new Color(125, 31, 31), 2));
        b.setAlignmentX(JButton.CENTER_ALIGNMENT);
        b.setAlignmentY(JButton.CENTER_ALIGNMENT);
        b.setFont(new Font("Gill Sans MT", Font.BOLD, 38));

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

    private ImageIcon getTitleImage() {
        try {
            return new ImageIcon(ImageIO.read(TitlePanel.class.getResource("/images/title.png")));
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
}