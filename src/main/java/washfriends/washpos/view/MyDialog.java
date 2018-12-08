package washfriends.washpos.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import washfriends.washpos.App;
import washfriends.washpos.api.DBManager;
import washfriends.washpos.api.FirebaseException;
import washfriends.washpos.controller.MultiPanel;
import washfriends.washpos.controller.MyFont;
import washfriends.washpos.controller.UIToolbox;
import washfriends.washpos.uitool.HorizontalButton;


/**
 * Base definition of the an JDialog.
 * setAlwaysOnTop method makes top of container.
 */
public class MyDialog {
	
	private JDialog dialog; 
	private String name;
	
	private MyDialog() {}
	
	public MyDialog(final String name, final String pin) {
		dialog = new JDialog();	
        Dimension screenSize = UIToolbox.getScreenSize();
        dialog.setSize(400, 400);
        JPanel panel = new JPanel(new BorderLayout());
           
        int x = (screenSize.width - dialog.getWidth()) / 2;
        int y = (screenSize.height - dialog.getHeight()) / 2;
        dialog.setLocation(x, y);
           
        final JLabel jlabel = new JLabel("등록하시겠습니까?");
        jlabel.setFont(MyFont.BOLD_FONT.deriveFont((float)40));
        panel.add(jlabel, BorderLayout.PAGE_START);

        JTextField pidLabel = new JTextField(name);
        pidLabel.setFont(MyFont.BOLD_FONT.deriveFont((float)50));
        pidLabel.setPreferredSize(new Dimension(120, 20)); 
        pidLabel.setEnabled(false);
        panel.add(pidLabel, BorderLayout.CENTER);
           
        HorizontalButton okbtn = new HorizontalButton("OK", "OK", "등록");
        okbtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
        			dialog.setVisible(false);
        			DBManager.DB.updateUser(name, pin);
        			MultiPanel.SELF.show("HOMEVIEW");
        		}catch(FirebaseException ex) {
        			App.LOGGER.warning(ex.getMessage());
        		}
   			}        	
        });
        HorizontalButton nobtn = new HorizontalButton("NO", "NO","취소");
        nobtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dialog.setVisible(false);
        	}        	
        });
        JPanel nav = new JPanel();
        nav.add(okbtn);
        nav.add(nobtn);
           
        panel.add(nav, BorderLayout.PAGE_END);
           
        dialog.setUndecorated(true);
        dialog.getContentPane().add(panel);
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
	}
	public void setDialog(boolean isVisible) {
		if (isVisible) {
			dialog.setVisible(true);			
		}else {
			dialog.setVisible(false);
		}
	}
	public MyDialog getDialog() {
		return this;
	}
	
	public static void main(String [] args) {
		MyDialog myDialog = new MyDialog("01043180221", "1234");
	}
}
