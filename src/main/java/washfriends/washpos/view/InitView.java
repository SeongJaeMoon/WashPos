package washfriends.washpos.view;

import washfriends.washpos.App;
import washfriends.washpos.controller.*;
import washfriends.washpos.uitool.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

// Initialize View
// Facilitates action listeners for button actions.
public class InitView extends JPanel implements ActionListener {
	
	protected final TitlePanel TITLE = new TitlePanel(); // Reference to title panel
	
    /**
     * Constructs a new view.
     * Sets the title and add itself to the multi-panel controller.
     *
     * @param tp        reference to the shared title pane
     * @param name      the lookup name associated with the view
     * @param text      the title text
     */
	public InitView(String name, String location, String info) {
		setLayout(new BorderLayout());
        setName(name);
        if(location != null) {
        	TITLE.setLocation(location);
        }
        if(info != null) {
        	TITLE.setInfomation(info);
        }
        add(TITLE, BorderLayout.NORTH);
	}
	
	/**
     * Prepare the view for displaying.
     * Invoked by MultiPanel.show before view is displayed.
     *
     * @param args      arguments needed to prepare the view
     * @return          true if logged in, otherwise false.
     */
    public boolean prepareView(Object... args) {
        if (App.USER == null) {
            MultiPanel.SELF.show("WELCOME");
            //MultiPanel.SELF.show("LOGIN");
            return false;
        }
        // TITLE.setUserTag(App.USER);
        return true;
    }

    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton)e.getSource();
        String name = button.getName();
        if (name == "EXIT") {
            if (App.USER.logout()) {
                App.USER = null;
                MultiPanel.SELF.show("WELCOME");
                // MultiPanel.SELF.show("LOGIN");
            }
        } else {
            MultiPanel.SELF.show(name);
        }
    }
}
