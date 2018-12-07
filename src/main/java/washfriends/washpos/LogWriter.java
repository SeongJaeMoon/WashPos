package washfriends.washpos;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
//import java.text.SimpleDateFormat;

public class LogWriter {
	static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;

    static private FileHandler fileHTML;
    static private Formatter formatterHTML;

    static public void setup() throws IOException {
    	
        // get the global logger to configure it
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        // suppress the logging output to the console
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }

        logger.setLevel(Level.INFO);
        fileTxt = new FileHandler("Logging" +  
        		  calcDate(System.currentTimeMillis())+ ".txt");
        fileHTML = new FileHandler("Logging" + 
        		  calcDate(System.currentTimeMillis()) + ".html");

        // create a TXT formatter
        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);

        // create an HTML formatter
        formatterHTML = new HtmlFormatter();
        fileHTML.setFormatter(formatterHTML);
        logger.addHandler(fileHTML);
    }
    
    private static String calcDate(long millisecs) {
        SimpleDateFormat date_format = new SimpleDateFormat("yyyyMMdd-HH_mm_ss", Locale.KOREA);
        Date resultdate = new Date(millisecs);
        return date_format.format(resultdate);
    }
    
    /**
    * set the LogLevel to Severe, only severe Messages will be written
    *
    * LOGGER.setLevel(Level.SEVERE);
    * LOGGER.severe("Info Log");
    * LOGGER.warning("Info Log");
    * LOGGER.info("Info Log");
    * LOGGER.finest("Really not important");
    *
    * set the LogLevel to Info, severe, warning and info will be written
    * finest is still not written
    *
    * LOGGER.setLevel(Level.INFO);
    * LOGGER.severe("Info Log");
    * LOGGER.warning("Info Log");
    * LOGGER.info("Info Log");
    * LOGGER.finest("Really not important");
    */
}
