package ru.bm.eetp.logging;

public class LoggerImpl implements Logger {
    @Override
    public void logIncoming(String prefix, String message) {
        System.out.println("\nINCOMING: "+prefix+" => "+message+"\n");
        //TO-DO -- тут добавить запись в нужный файл. Вывод в консоль убирать не нужно!
    }

    @Override
    public void logOutcoming(String prefix, String message) {
        System.out.println("\nOUTCOMING: "+prefix+" => "+message+"\n");
        //TO-DO -- тут добавить запись в нужный файл. Вывод в консоль убирать не нужно!
    }

    @Override
    public void logError(String prefix, String message) {
        System.out.println("\nERROR: "+prefix+" => "+message+"\n");
        //TO-DO -- тут добавить запись в нужный файл. Вывод в консоль убирать не нужно!
    }
}
