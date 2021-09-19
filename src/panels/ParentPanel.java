package panels;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

/**
 * Class that holds all JPanels under a CardLayout
 * Allows program to switch between multiple JPanels without having to do all of it under a single paint method
 */
public class ParentPanel extends JPanel {

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
        helpPanel = new HelpPanel();
        hasStarted = false;

        add(titlePanel, "title");

        cl.show(this, "title");
    }

     /*
        PANEL NAMES:
        title, help, menu, card, game info
     */

    /**
     * Changes the JPanel.
     * Panel Names: title, menu, card
     * @param panelName The name of the JPanel
     */
    public void changePanel(String panelName) {
        System.out.println("Moving to " + panelName);
        cl.show(this, panelName.toLowerCase());
    }

    public void initializeMenuPanel() {
        menuPanel = new MenuPanel(this);
    }
}