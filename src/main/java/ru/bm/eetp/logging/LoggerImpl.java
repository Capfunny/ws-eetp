package ru.bm.eetp.logging;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.bm.eetp.config.LoggingProperties;
import ru.bm.eetp.logging.impl.XloggerError;
import ru.bm.eetp.logging.impl.XloggerTrace;

@Component
public class LoggerImpl implements Logger {

    private org.slf4j.Logger loggerTrace = LoggerFactory.getLogger(XloggerTrace.class);
    private org.slf4j.Logger loggerError = LoggerFactory.getLogger(XloggerError.class);

    private LoggingProperties loggingProperties;

    public LoggerImpl(LoggingProperties loggingProperties) {
        this.loggingProperties = loggingProperties;
    }

    @Override
    public void logIncoming(String comtext) {
        if(incomingLog()) {
            String msg = "INCOMING => " + comtext;
            System.out.println("\n" + msg + "\n");
            //TO-DO -- тут добавить запись в нужный файл. Вывод в консоль убирать не нужно!
            loggerTrace.info(msg);
        }
    }

    @Override
    public void logOutcoming(String context) {
        if(outcomingLog()) {
            String msg = "OUTCOMING => " + context;
            System.out.println("\n" + msg + "\n");
            //TO-DO -- тут добавить запись в нужный файл. Вывод в консоль убирать не нужно!
            loggerTrace.info(msg);
        }
    }

    @Override
    public void logError(String errorMessage, String context) {
        if(errorLog()) {
            String msg = "ERROR => " + errorMessage + "\nERROR =>" + context;
            System.out.println("\nERROR: " + msg + "\n");
            //TO-DO -- тут добавить запись в нужный файл. Вывод в консоль убирать не нужно!
            loggerError.info(msg);
        }
    }

    /**
     * 0 – Логируются только ошибочные сообщения;
     * 1 – Логируются все сообщения, как входящие, так и исходящие, (включая квитанции - это исходящие);
     * 2 – Логируются входящие и ошибочные сообщения;
     * 3 – Логирование отключено.
     *
     * @return
     */
    private boolean errorLog() {
        return loggingProperties.getLogMode() == 0
                || loggingProperties.getLogMode() == 2;
    }

    private boolean incomingLog() {
        return loggingProperties.getLogMode() == 1
                || loggingProperties.getLogMode() == 2;
    }

    private boolean outcomingLog() {
        return loggingProperties.getLogMode() == 1;
    }
}
