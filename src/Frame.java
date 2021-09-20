import panels.ParentPanel;

import javax.swing.*;

public class Frame extends JFrame {


    public Frame(String title) {
        super(title);
        pack();

        ParentPanel p = new ParentPanel();

        add(p);
        pack();
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

//        if (screenSize.getHeight() < 1000) {
//            setExtendedState(JFrame.MAXIMIZED_BOTH);
//        } else {
//            setPreferredSize(new Dimension(1000, 1000));
//        }

        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}