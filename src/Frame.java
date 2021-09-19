import panels.ParentPanel;

import javax.swing.*;

public class Frame extends JFrame {


    public Frame(String title) {
        super(title);
        pack();

        ParentPanel p = new ParentPanel();

        add(p);
        pack();
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}