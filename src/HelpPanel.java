import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpPanel extends JPanel {
	
	private int page;
	private boolean exit;
	private JButton backButton;
	private JButton exitButton;
	private JButton nextButton;
	private JDialog dialog;
	
    public HelpPanel(ParentPanel panel) {
    	page = 1;
        exit = false;

        backButton = new JButton("Back");
        exitButton = new JButton("Exit");
        nextButton = new JButton("Next");
        dialog = null;       
    	
    }
    
    public void showDialog() {
    	page = 1;
    	exit = false;
    	setListeners();
    	changePage(page);
    }
    
    private void changePage(int page) {

        Object[] options = null;
        String msg = "";
        String title = "";

        switch (page) {
            case 1:
                options = new Object[]{exitButton, nextButton};
                msg = "Press START to start the bingo generator. Input the required parameters before generating: the game number, the number of cards, the number of days, and the number of winners. \r\n"
                		+ "\r\n"
                		+ "Game number: determines the sequence of the randomly generated numbers\r\n"
                		+ "\r\n"
                		+ "Then, select the folder to place the generated files in. You may create a new folder or select an already existing folder. After selecting the folder and pressing open, start generating the files by clicking OK on the next popup. \r\n";
                break;
            case 2:
                options = new Object[]{backButton, exitButton, nextButton};
                msg = "Once the files have been successfully created, you will be redirected to the Game Menu screen. The Game Menu has three buttons: Open Directory, View Card, and Game Info. \r\n"
                		+ "\r\n"
                		+ "Open Directory: Opens the folder where the generated files are located\r\n"
                		+ "View Card: View any generated card by inputting the card ID. \r\n"
                		+ "Game Info: Displays the game’s parameters, the bingo ball sequence each day, and the winners each day.\r\n";                		
                break;
            case 3:
                options = new Object[]{backButton, exitButton};
                msg = "View Card displays the generated bingo card of the specified ID. The View Card screen has a menu that can either be opened by right-clicking the screen or selecting Card on the top menu bar. The menu has three main buttons: Open, Info, and Toggle Markers. \r\n"
                		+ "\r\n"
                		+ "Open: Opens the PNG file of the card.\r\n"
                		+ "Info: Displays the card info of the card ID, if the card wins, the day the card wins, and the round the card wins\r\n";
                break;
        }

        JTextArea textArea = new JTextArea(msg);
        textArea.setEditable(false);
        textArea.setFont(textArea.getFont().deriveFont(16f));
        JScrollPane sp = new JScrollPane(textArea);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JOptionPane optionPane = new JOptionPane(sp);
        optionPane.setMessageType(JOptionPane.PLAIN_MESSAGE);
        optionPane.setPreferredSize(new Dimension(450, 250));
        optionPane.setOptions(options);


        dialog = optionPane.createDialog(null, "Game Info (" + page + "/3)");

        if (!exit) {
            dialog.setModal(true);
            dialog.setVisible(true);
        }

    }
    
    private void setListeners() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Back clicked");
                dialog.dispose();
                page--;
                changePage(page);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Exit clicked");
                dialog.dispose();
                exit = true;
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!exit) {
                    dialog.dispose();
                    page++;
                    changePage(page);
                } else {
                    dialog.dispose();
                }

            }
        });
    }
}