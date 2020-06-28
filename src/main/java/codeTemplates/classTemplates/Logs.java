package classTemplates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Logs {

    static Logger log = LogManager.getLogger(Logs.class.getName());

    public static void main(String[] args) {
        log.debug("This is a debug log");
    }
}
