package washfriends.washpos.uitool;

import java.awt.*;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.*;
import washfriends.washpos.controller.*;
import washfriends.washpos.model.*;

public class TitlePanel extends JPanel {
	
	private static final int HORIZONTAL_MARGIN = 100;            // Horizontal padding on the left and right edges
    private static final int PADDING           = 20;             // Internal padding
    private static String HTML                 = null;           // HTML for about label
    
    private static final int TITLE_SIZE     = 60;  // Font size of the title
    private static final int SUBTITLE_SIZE  = 40;  // Font size of the subtitle
    private final JButton LOCATION;       // label of the title of the view.
    private final JButton INFOMATION;    // label of the name of the user logged in.
    
    /**
     * Construct the title panel of the views.
     * @throws IOException 
     */
    public TitlePanel() {
        JPanel inner = new JPanel();
        inner.setBackground(UITheme.TITLE_COLOR);
        inner.setLayout(new GridLayout(1, 3));
        
        LOCATION = new JButton();
        LOCATION.setBackground(UITheme.BG_COLOR);
        LOCATION.setFont(MyFont.REGULAR_FONT.deriveFont((float)SUBTITLE_SIZE));
        LOCATION.setText("수원 금곡점");
        inner.add(LOCATION);
        
        JButton LOGO = new JButton();
        LOGO.setBackground(UITheme.BG_COLOR);
        LOGO.setIcon(new ImageIcon(getClass().getResource("/assets/images/logo_bottom.png")));
        inner.add(LOGO);
        
        INFOMATION = new JButton();
        INFOMATION.setBackground(UITheme.BG_COLOR);
        INFOMATION.setFont(MyFont.REGULAR_FONT.deriveFont((float)30));
        INFOMATION.setText("<html><font color='green'>회원 가입하고</font> " +
        				  "<font color='red'>포인트</font> " +
        				   "<font color='green'>받자!</font></html>"); 
        inner.add(INFOMATION);

        UIToolbox.setSize(inner, new Dimension(
                UIToolbox.getScreenSize().width - HORIZONTAL_MARGIN,
                LOCATION.getFontMetrics(LOCATION.getFont()).getHeight() +
                INFOMATION.getFontMetrics(INFOMATION.getFont()).getHeight() + PADDING * 2 + 10));
        
        UIToolbox.box(this, inner);
    }

//    private String getHTML() {
//        if (HTML != null) {return HTML;}
//        HTML = UIToolbox.getHTML("/assets/htdocs/about.html");
//        return HTML;
//    }
    
    public void setLocation(String text) {
    	LOCATION.setText(text);
    }
    
    public void setInfomation(String text) {
    	INFOMATION.setText(text);
    }
    
//    public void setUserTag(User user) {
//        USER_TAG.setText((user == null) ? "" : "Logged in: " + user.getPid());
//    }
}
