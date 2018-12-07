package washfriends.washpos.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;
import javax.swing.*;

import washfriends.washpos.App;
import washfriends.washpos.controller.*;

public class WelcomeView extends InitView {

	public WelcomeView() {
		super("WELCOME", null, null);
		App.LOGGER.info("Set Layout WelcomeView");
	    
		JPanel inner = new JPanel();
		inner.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.NORTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.1;
		
	    JLabel title = new JLabel();
	    title.setText(UIToolbox.getHTML("/assets/htdocs/title.html")); 
	    
	    inner.add(title, c);
	    
        // SignIn.
        JButton oldUser = new JButton();
        oldUser.setName("oldUser");
        oldUser.setFont(MyFont.REGULAR_FONT.deriveFont((float)50));
        oldUser.setText("<html><font color='black' size='70'>회원 서비스</font><br>" +
				"<p style='border:1px green solid;'><font color='green' size='30'>서비스 이용에 따라<br>할인 혜택인<br>포인트 제공</font></p>" +
				"<html>");
        oldUser.addActionListener(this);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        
        inner.add(oldUser, c);
        
        // SignUp.
        JButton newUser = new JButton();
        newUser.setName("newUser");
        newUser.setFont(MyFont.REGULAR_FONT.deriveFont((float)50));
        newUser.setText("<html><font color='black' size='70'>회원 신규 등록</font><br>" +
				"<p style='border:1px red solid;'><font color='red' size='30'>신규 등록 시<br>핸드폰 번호와<br>비밀번호만 입력</font></p>" +
				"<html>");
        newUser.addActionListener(this);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        
        inner.add(newUser, c);
        
        // Change Locale between Korea and English.
        JButton changeLocale = new JButton();
	    changeLocale.setName("Locale");
	    changeLocale.setText("<html><p style='border:1px blue solid;'>" + 
	    			"<font color='white' size='50'>English</font></p></html>");
	    changeLocale.setBackground(Color.BLUE);
        changeLocale.addActionListener(this);
	    
        c.anchor = GridBagConstraints.PAGE_END;
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 2;
        
	    inner.add(changeLocale, c);
	    
        add(inner);
	}
	@Override
    public boolean prepareView(Object... args) {
        return false; // Do nothing
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	JButton button = (JButton)e.getSource();
        String name = button.getName();
        if(name.equals("newUser")) {
        	App.LOGGER.info("newUser action click");
        	MultiPanel.SELF.show("SIGNUP");
        }else if(name.equals("oldUser")) {
        	App.LOGGER.info("oldUser action click");
        	MultiPanel.SELF.show("SIGNIN");
        }else if(name.equals("changeLocale")) {
        	App.LOGGER.info("changeLocale action click");
        	// Locale Setting
        }
    }
}
