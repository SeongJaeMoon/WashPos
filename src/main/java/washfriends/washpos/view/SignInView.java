package washfriends.washpos.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import washfriends.washpos.App;
import washfriends.washpos.api.DBManager;
import washfriends.washpos.api.FirebaseException;
import washfriends.washpos.controller.MultiPanel;
import washfriends.washpos.controller.MyFont;
import washfriends.washpos.controller.TimeOut;
import washfriends.washpos.controller.UIToolbox;
import washfriends.washpos.model.User;
import washfriends.washpos.uitool.HorizontalButton;
import washfriends.washpos.uitool.InputField;
import washfriends.washpos.uitool.NumericKeyboard;

public class SignInView extends InitView{
	
	private final JTextField USER;          	// User PID field
	private final InputField USER_FIELD;    	// input field for user student ID
	private final JPasswordField PIN;       	// Access PIN field
	private final InputField PIN_FIELD;     	// input filed for user PIN
	private JLabel label;
	private TimeOut timeout = null;				// No reaction waiting time  
	private TimeOut timer = null;				// Server reaction waiting time
	
	public SignInView() {
		super("SIGNIN", null, "회원정보입력");
		App.LOGGER.info("Set Layout SignInView");
		
		label = new JLabel("잠시만 기다려주세요...");
		label.setFont(MyFont.REGULAR_FONT.deriveFont((float)20));
		
		NumericKeyboard kb = new NumericKeyboard(null);
		JPanel main = new JPanel();
		
		main.setLayout(new GridBagLayout());
		main.add(label);
		label.setVisible(false);
		
		JPanel inner = new JPanel();
        inner.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 0));
        JPanel form = new JPanel(new GridLayout(2, 1));
        USER = new JTextField();
        
        USER.addCaretListener(new CaretListener() {
        	public void caretUpdate(CaretEvent e) {
        		USER_FIELD.showError(false);
        		String text = USER.getText();
        		try {
        			if (text.length() > 11) {
        				USER.setText(text.substring(0, 11));
        			}
        		} catch (Exception exe) {}
        	}
        });
        PIN = new JPasswordField();
        PIN.addCaretListener(new CaretListener() {
        	public void caretUpdate(CaretEvent e) {
        		PIN_FIELD.showError(false);
        		String text = new String(PIN.getPassword());
        		try {
        			if (text.length() > 4) {
        				PIN.setText(text.substring(0, 4));
        			}
        		} catch (Exception exe) {}
        	}
        });
        form.add(USER_FIELD = new InputField(USER, "전화번호('-'없이 입력해주세요.)", 60, 16, 500, false));
        form.add(PIN_FIELD = new InputField(PIN, "비밀번호(4자리)", 60, 16, 500, false));
	
        inner.add(form);
        inner.add(kb);
        add(UIToolbox.box(main, inner), BorderLayout.CENTER);
        
        JPanel nav = new JPanel();

        nav.add(new HorizontalButton("ENTER", "CHECK", "시작", this));
        nav.add(new HorizontalButton("BACK", "BACK", "취소", this));
        add(nav, BorderLayout.SOUTH);
        
        // attach event listeners
        USER.addFocusListener(kb);
        PIN.addFocusListener(kb);
        this.addMouseMotionListener(new MouseAdapter() {
        	void reset() {
        		if (timeout != null) {
					timeout.reset();
				}
        	}
        	@Override public void mousePressed(MouseEvent e) {reset();}
		    @Override public void mouseClicked(MouseEvent e) {reset();}
		    @Override public void mouseWheelMoved(MouseWheelEvent e) {reset();}
		    @Override public void mouseMoved(MouseEvent e) {reset();}
		    @Override public void mouseDragged(MouseEvent e) {reset();}
		});
	}
	
	@Override
    public boolean prepareView(Object... args) {
		if (timeout != null) {
            timeout.stop();
        }
		if (timer != null) {
			timer.stop();
		}
        USER.setText(""); USER_FIELD.showError(false);
        PIN.setText(""); PIN_FIELD.showError(false);
        USER.requestFocusInWindow();
        timeout = new TimeOut(new Runnable() {
            public void run() {
                MultiPanel.SELF.show("WELCOME");
            }
        }, 60 * 1000).start();
        return false;
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		 JButton button = (JButton)e.getSource();
	     String name = button.getName();
	     if(name.equals("ENTER")) {
	    	 App.LOGGER.info("SignIn enter action click");
	    	 try {
	    		 App.USER = new User(name, "1234", 0, 0);
	    		 MultiPanel.SELF.show("HOMEVIEW");
//	    		 // PID check
//		        	final String username = new String(USER.getText());
//		            if (!username.isEmpty() && username.length() == 11) {
//		            	App.LOGGER.info("PID Check ok");
//		            	// PIN check
//			            final String password = new String(PIN.getPassword());
//			            if (!password.isEmpty() && password.length() == 4) {
//			            	App.LOGGER.info("PIN Check ok");
//			            	try {
//			            		DBManager.DB.getUser(username, password);
//			            		label.setVisible(true);
//			            		App.LOGGER.info("waiting for 5 seconds");
//			            		timer = new TimeOut(new Runnable() {
//			                        public void run() {
//					            		App.LOGGER.info("5 seconds done");
//					            		label.setVisible(false);
//					            		boolean isExists = (App.USER != null) ? true : false; 
//			            				App.LOGGER.info(isExists ? "true" : "false" + " isExists value");
//			            					// User Exists
//			   		    	            if(isExists) {
//			   		    	            	App.LOGGER.warning(username + " is value of exists in db");
//			   		    	            	MultiPanel.SELF.show("HOMEVIEW");
//			   		    	            }else {
//			   		    	            	// User adds
//			   		    	            	App.LOGGER.info(isExists ? "true" : "false" + " Empty user");
//			   		    	            	USER_FIELD.errorDetail(true, "일치하는 회원 정보가 없습니다."); 
//			   		    	            	USER_FIELD.showError(true);
//			   		    	            	PIN_FIELD.showError(true);
//			   		    	            }
//			                        }
//			                    }, 5 * 1000).start();
//		            		}catch(FirebaseException ex){
//		            			App.LOGGER.warning(ex.getMessage());
//		            			if (timer != null) {
//		            				timer.stop();
//		            			}
//		            		}
//			            }else {
//			            	PIN_FIELD.showError(true);
//			            }
//		            }else {
//		            	USER_FIELD.showError(true);
//		            }
	    	 }catch(Exception ex) {
	    		 App.LOGGER.warning("ENTER Exception " + ex.getMessage());
	    	 }
	     }else if(name.equals("BACK")) {
	    	 if (timeout != null) {
	    		 timeout.stop();
	    	 }
	    	 if (timer != null) {
	    		 timer.stop();
	    	 }
	    	 MultiPanel.SELF.show("WELCOME");
	     }
	 }

}
