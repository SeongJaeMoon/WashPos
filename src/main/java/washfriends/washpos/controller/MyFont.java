package washfriends.washpos.controller;

import java.awt.*;
import java.io.*;
import java.util.*;

/**
 * This class provides access to custom font faces.
 * Custom font faces include:
 *  - NanumGothic-Bold
 *  - NanumGothic-ExtraBold
 *  - NanumGothic-Regular
 */
public class MyFont {

	private static final MyFont myFont;
	private static final String FONT_PATH           = "/assets/fonts";          
	private static final String BOLD_FONT_URI		= FONT_PATH + "/NanumGothic-Bold.ttf";
	private static final String EXTRABOLD_FONT_URI	= FONT_PATH + "/NanumGothic-ExtraBold.ttf";
	private static final String REGULAR_FONT_URI    = FONT_PATH + "/NanumGothic-Regular.ttf";
	private static final String ICON_FONT_URI 		= FONT_PATH + "/MyIcons.ttf";
	
	// Setup the fonts families
	public static final Font BOLD_FONT;
	public static final Font EXTRABOLD_FONT;
    public static final Font REGULAR_FONT;
    public static final Font ICON_FONT;
    
    static {
		myFont = new MyFont();
		BOLD_FONT = myFont.makeFont(BOLD_FONT_URI);
		EXTRABOLD_FONT = myFont.makeFont(EXTRABOLD_FONT_URI);
		REGULAR_FONT = myFont.makeFont(REGULAR_FONT_URI);
		ICON_FONT = myFont.makeFont(ICON_FONT_URI);
	}
    
    public static final Map<String, String> ICONS = new HashMap<String, String>();
    
    static {
        ICONS.put("SPACE",  " ");
        ICONS.put("PREV",   "\ue000");
        ICONS.put("NEXT",   "\ue001");
        ICONS.put("BKSP",   "\ue002");
        ICONS.put("BACK",   "\ue003");
        ICONS.put("FORW",   "\ue004"); 
        ICONS.put("ENTER",  "\ue004");
        ICONS.put("SHIFT",  "\ue005");
        ICONS.put("GOOD",   "\ue006");
        ICONS.put("ERROR",  "\ue007");
        ICONS.put("ADD",    "\ue008");
        ICONS.put("NOTIF",  "\ue009");
        ICONS.put("HOME",   "\ue00a");
        ICONS.put("USER",   "\ue00b");
        ICONS.put("CAR",    "\ue00c");
        ICONS.put("METER",  "\ue00d");
        ICONS.put("MAIL",   "\ue00e");
        ICONS.put("HELP",   "\ue00f");
        ICONS.put("INFO",   "\ue010");
        ICONS.put("EDIT",   "\ue011");
        ICONS.put("TRASH",  "\ue012");
        ICONS.put("EXIT",   "\ue013");
        ICONS.put("NEW",    "\ue014");
        ICONS.put("FILES",  "\ue015");
        ICONS.put("ALERT",  "\ue016");
        ICONS.put("KEYB",   "\ue017");
        ICONS.put("KEY",    "\ue018");
        ICONS.put("CHECK",  "\ue019");
        ICONS.put("CROSS",  "\ue01a");
        ICONS.put("SHIELD", "\ue01b");
        ICONS.put("DOLLAR", "\ue01c");
    }
    
    private MyFont() {}
    
    private Font makeFont(String fontURI) {
    	Font font = null;
        try {
        	font = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream(fontURI));
            GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            genv.registerFont(font);
        } catch (FontFormatException e) {
            e.printStackTrace(System.err);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return font;
    }
}
