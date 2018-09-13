package ru.bm.eetp.logging;

public interface Logger {

    void logIncoming(String prefix, String message);

    void logOutcoming(String prexi, String message);

    void logError(String prefix, String message);

}
