package washfriends.washpos;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import washfriends.washpos.model.*;
import washfriends.washpos.controller.*;
import washfriends.washpos.view.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * License : MIT License. 
 * Copyright 2018. WashFriends Co. all rights reserved.
 *  
 * if you have any questions and concerns, 
 * please contact by seongjae.m@gmail.com.
 */
public class App {
	
	// use the classname for the logger, this way you can refactor
    public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public static User USER;
	
	public static void createAndShowGUI() {
		LOGGER.setLevel(Level.INFO);
		LOGGER.info("logging start");
        UITheme.setLookAndFeel();
        JFrame frame = new JFrame();
        UIToolbox.fullscreen(frame);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override 
            public void windowClosing(WindowEvent e) {
            	LOGGER.info("DB Destroyed1");
            }
        });
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyEventDispatcher() {
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                	LOGGER.info("logging finished");
                	System.exit(0);
                }
                return false;
            }
        });
        frame.setLayout(MultiPanel.SELF.setParent(frame));
        
        final String DB_PATH = "/assets/htdocs/note-70834-firebase-adminsdk-ii9hi-d9ab85ec35.json";
		try {
    		FirebaseOptions options = new FirebaseOptions.Builder()
    				.setCredentials(GoogleCredentials.fromStream(App.class.getResourceAsStream(DB_PATH)))
    				.setDatabaseUrl("https://note-70834.firebaseio.com")
    				.build();
    		FirebaseApp.initializeApp(options);
        } catch (Exception e) {
        	LOGGER.warning("DBManager failed init " + e.getMessage());
        }
		LOGGER.info("DBManager initialized");
        
        
        LOGGER.info("set Layout Parent");
        MultiPanel.SELF.add(new WelcomeView());
        MultiPanel.SELF.add(new SignUpView());        
        MultiPanel.SELF.add(new SignInView());  
        MultiPanel.SELF.add(new HomeView());
        
        frame.pack();
        frame.setVisible(true);
        
    }
	
    public static void main( String[] args ){
    	SwingUtilities.invokeLater(new Runnable() {	
    	public void run() {
    		try {
    			LogWriter.setup();
    		} catch (IOException e) {
    			e.printStackTrace();
    			throw new RuntimeException("Problems with creating the log files");
    		}
    		createAndShowGUI(); 
    	}
    });
    }
}
