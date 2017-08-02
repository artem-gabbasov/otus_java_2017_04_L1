package ru.otus.customsorter;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by Artem Gabbasov on 02.08.2017.
 */
public class SPCounterHandler extends Handler {
    private int serialsCount;
    private int parallelsCount;

    public SPCounterHandler() {
        serialsCount = 0;
        parallelsCount = 0;
    }

    @Override
    public void publish(LogRecord record) {
        if (record.getMessage().equals("s")) serialsCount++;
        else if (record.getMessage().equals("p")) parallelsCount++;
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() throws SecurityException {
    }

    public void printStatistics() {
        System.out.println("Serial calls: " + serialsCount);
        System.out.println("Parallel calls: " + parallelsCount);
    }

    public int getSerialsCount() {
        return serialsCount;
    }

    public int getParallelsCount() {
        return parallelsCount;
    }
}
