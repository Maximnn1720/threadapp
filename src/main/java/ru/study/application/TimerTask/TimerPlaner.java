package ru.study.application.TimerTask;

import ru.study.application.Logger.LogWriter;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TimerPlaner extends Thread {
    private final LogWriter logger;
    private final List<TimerListener> listeners;
    private final ExecutorService executorService;

    public TimerPlaner(LogWriter logger) {
        this.logger = logger;
        this.listeners = new ArrayList<>();
        this.executorService = Executors.newCachedThreadPool();
    }

    public void scheduleWork(Integer repeatPeriodOfSeconds, Runnable runnable) {
        TimerExecutor timerExecutor = new TimerExecutor(repeatPeriodOfSeconds, runnable, logger);
        listeners.add(timerExecutor);
        executorService.execute(timerExecutor);
    }

    @Override
    public void run() {
        LocalTime showingTime = LocalTime.from(Instant.now().atZone(ZoneId.of("GMT+3"))).truncatedTo(ChronoUnit.SECONDS);
        while (!this.isInterrupted()) {
            LocalTime currentTime = LocalTime.from(Instant.now().atZone(ZoneId.of("GMT+3"))).truncatedTo(ChronoUnit.SECONDS);
            if (showingTime.compareTo(currentTime) < 0) {
                showingTime = currentTime;
                incrementSecond();
            }
        }
    }

    private void incrementSecond() {
        listeners.forEach(TimerListener::increment);
    }

    public void stopTimer() {
        executorService.shutdownNow();
        this.interrupt();
    }
}
