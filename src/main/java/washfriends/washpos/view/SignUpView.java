package washfriends.washpos.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import washfriends.washpos.App;
import washfriends.washpos.api.DBManager;
import washfriends.washpos.api.FirebaseException;
import washfriends.washpos.controller.*;
import washfriends.washpos.uitool.HorizontalButton;
import washfriends.washpos.uitool.InputField;
import washfriends.washpos.uitool.NumericKeyboard;

public class SignUpView extends InitView {

	private final JTextField USER;          	// User PID field
	private final InputField USER_FIELD;    	// input field for user student ID
	private final JPasswordField PIN;       	// Access PIN field
	private final InputField PIN_FIELD;     	// input filed for user PIN
	private final JPasswordField CONFIRM_PIN; 	// Confirm access PIN field
	private final InputField CONFIRM_PIN_FIELD; // input filed for confirm user PIN
	private JLabel label;
	// No reaction waiting time
	private TimeOut timeout = null;
	// Server reaction waiting time  
	private TimeOut timer = null;
	private MyDialog dialog = null;
	
	public SignUpView() {
		super("SIGNUP", null, "회원가입");
		App.LOGGER.info("Set Layout SignUpView");
		
		label = new JLabel("잠시만 기다려주세요...");
		label.setFont(MyFont.REGULAR_FONT.deriveFont((float)20));
		
		NumericKeyboard kb = new NumericKeyboard(null);
		JPanel main = new JPanel();
		
		main.setLayout(new GridBagLayout());
		main.add(label);
		label.setVisible(false);
		
		JPanel inner = new JPanel();
        inner.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 0));
        JPanel form = new JPanel(new GridLayout(3, 1));
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
        CONFIRM_PIN = new JPasswordField();
        CONFIRM_PIN.addCaretListener(new CaretListener() {
        	public void caretUpdate(CaretEvent e) {
        		CONFIRM_PIN_FIELD.showError(false);
        		String text = new String(CONFIRM_PIN.getPassword());
        		try {
        			if (text.length() > 4) {
        				PIN.setText(text.substring(0, 4));
        			}
        		} catch (Exception exe) {}
        	}
        });
        
        form.add(USER_FIELD = new InputField(USER, "전화번호('-'없이 입력해주세요.)", 60, 16, 500, false));
        form.add(PIN_FIELD = new InputField(PIN, "비밀번호(4자리)", 60, 16, 500, false));
        form.add(CONFIRM_PIN_FIELD = new InputField(CONFIRM_PIN, "비밀번호 확인", 60, 16, 500, false));    
        
        inner.add(form);
        inner.add(kb);
        add(UIToolbox.box(main, inner), BorderLayout.CENTER);
        
        JPanel nav = new JPanel();

        nav.add(new HorizontalButton("ENTER", "CHECK", "등록", this));
        nav.add(new HorizontalButton("BACK", "BACK", "취소", this));
        add(nav, BorderLayout.SOUTH);
		    
        // attach event listeners
        USER.addFocusListener(kb);
        PIN.addFocusListener(kb);
        CONFIRM_PIN.addFocusListener(kb);
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
		if (dialog != null) {
    		dialog.setDialog(false);
    	}
        USER.setText(""); USER_FIELD.showError(false);
        PIN.setText(""); PIN_FIELD.showError(false);
        CONFIRM_PIN.setText(""); CONFIRM_PIN_FIELD.showError(false);
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
        	App.LOGGER.info("Enter action click");
        	try {
        		// PID check
	        	final String username = new String(USER.getText());
	            if (!username.isEmpty() && username.length() == 11) {
	            	App.LOGGER.info("PID Check ok");
	            	// PIN check
		            final String password = new String(PIN.getPassword());
		            final String confirmPassword = new String(CONFIRM_PIN.getPassword());	        
		            if (!password.isEmpty() && password.length() == 4
		            	&& !confirmPassword.isEmpty() && confirmPassword.length() == 4) {
		            	
		            	if (confirmPassword.equals(password)) {
		            		App.LOGGER.info("PIN Check ok");
		            		try {
			            		DBManager.DB.userExists(username);
			            		label.setVisible(true);
			            		App.LOGGER.info("waiting for 5 seconds");
			            		timer = new TimeOut(new Runnable() {
			                        public void run() {
					            		App.LOGGER.info("5 seconds done");
					            		label.setVisible(false);
					            		boolean isExists = DBManager.DB.pidExists();
			            				App.LOGGER.info(isExists ? "true" : "false" + " isExists value");
			            					// User Exists
			   		    	            if(isExists) {
			   		    	            	App.LOGGER.warning(username + " is value of exists in db");
			   		    	            	USER_FIELD.errorDetail(true, "이미 등록된 회원 번호입니다."); USER_FIELD.showError(true);
			   		    	            	PIN.setText(""); 
			   		    	            	CONFIRM_PIN.setText("");
			   		    	            }else {
			   		    	            	// User adds
			   		    	            	App.LOGGER.info(isExists ? "true" : "false" + " User adds");
			   		    	            	dialog = new MyDialog(username);
			   		    	            }
			                        }
			                    }, 5 * 1000).start();
		            		}catch(FirebaseException ex){
		            			App.LOGGER.warning(ex.getMessage());
		            			if (timer != null) {
		            				timer.stop();
		            			}
		            		}
		            	}else {
		            		// No matches between pin and confirm pin.
		            		PIN_FIELD.showError(true);
		            		CONFIRM_PIN_FIELD.showError(true); CONFIRM_PIN_FIELD.errorDetail(true, "비밀번호가 일치하지 않습니다.");
		            		App.LOGGER.warning("No matches between pin and confirm pin.");
		            	}
		            }else {
		            	PIN_FIELD.showError(true);
		            	CONFIRM_PIN_FIELD.showError(true);
		            }
	            }else {
	            	USER_FIELD.showError(true);
	            }
        	}catch (Exception ex) {
        		App.LOGGER.warning("ENTER Exception " + ex.getMessage());
        	}
        }else if(name.equals("BACK")) {
        	if (timeout != null) {
        		timeout.stop();
        	}
        	if (timer != null) {
        		timer.stop();
        	}
        	if (dialog != null) {
        		dialog.setDialog(false);
        	}
        	MultiPanel.SELF.show("WELCOME");
        }
    }
}
