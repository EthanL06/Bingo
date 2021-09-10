import javax.swing.*;

public class Frame extends JFrame {


    public Frame(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();


//        TitlePanel p = new TitlePanel();
        ParentPanel p = new ParentPanel();

//
        add(p);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }
}