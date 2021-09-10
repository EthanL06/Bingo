import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

public class ParentPanel extends JPanel {

    /*
    Class that holds all JPanels under a CardLayout
    Allows to switch between multiple JPanels
     */

    private final TitlePanel titlePanel;
    private final HelpPanel helpPanel;
    private MenuPanel menuPanel;
    private final CardLayout cl;
    private final boolean hasStarted;

    public ParentPanel() {

        cl = new CardLayout();
        setLayout(cl);

        UIDefaults defaults = UIManager.getLookAndFeelDefaults();
        defaults.put("Button.focus", new ColorUIResource((new Color(0, 0, 0, 0))));

        titlePanel = new TitlePanel(this);
        helpPanel = new HelpPanel(this);
        hasStarted = false;

        add(titlePanel, "title");
        add(helpPanel, "help");

        cl.show(this, "title");
    }

     /*
        PANEL NAMES:
        title, help, menu, card, game info
     */

    public void changePanel(String panelName) {
        System.out.println("Moving to " + panelName);
        cl.show(this, panelName.toLowerCase());
    }

    public void initializeMenuPanel() {
        menuPanel = new MenuPanel(this);
    }
}