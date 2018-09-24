package ru.bm.eetp.logging;

public interface Logger {

    void logIncoming(String context);

    void logOutcoming(String context);

    void logError(String errorMessage, String context);

}
