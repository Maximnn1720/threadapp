package ru.study.application.TimerTask;

import ru.study.application.Logger.LogWriter;

public abstract class TimerTask implements Runnable {
    private final LogWriter logWriter;

    public TimerTask(LogWriter logWriter) {
        this.logWriter = logWriter;
    }
}
