package Util;

import java.util.logging.*;
import java.io.IOException;
import java.util.logging.Logger;

public class SimulationLogger {
    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;

    static private FileHandler fileHTML;
    static private Formatter formatterHTML;

    static public void setup() throws IOException {
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


        // suppress the logging output to the console
//        Logger rootLogger = Logger.getLogger("");
//        Handler[] handlers = rootLogger.getHandlers();
//        if (handlers[0] instanceof ConsoleHandler) {
//            rootLogger.removeHandler(handlers[0]);
//        }

//        logger.setLevel(Level.INFO);
//        fileTxt = new FileHandler("Logging.txt");
//        fileHTML = new FileHandler("Logging.html");
//
//        // create a TXT formatter
//        formatterTxt = new SimpleFormatter();
//        fileTxt.setFormatter(formatterTxt);
//        logger.addHandler(fileTxt);
//
//        // create an HTML formatter
//        formatterHTML = new SimulationLogFormatter();
//        fileHTML.setFormatter(formatterHTML);
//        logger.addHandler(fileHTML);

    }
}
