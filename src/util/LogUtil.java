package util;

import java.io.IOException;
import java.util.logging.*;

public class LogUtil {
    public static void setupLogger() {
        Logger rootLogger = Logger.getLogger("");
        Handler fileHandler;
        try {
            fileHandler = new FileHandler("app.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            rootLogger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
