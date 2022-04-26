package ru.study.application.TimerTask;

import ru.study.application.Logger.LogWriter;

import java.util.concurrent.atomic.AtomicInteger;

public class TimerExecutor implements Runnable, TimerListener {
    private final Integer period;
    private final Runnable runnable;
    private final LogWriter logger;
    private AtomicInteger counter;
    private AtomicInteger dingQueue;

    public TimerExecutor(Integer period, Runnable runnable, LogWriter logger) {
        this.period = period;
        this.runnable = runnable;
        this.logger = logger;
        this.counter = new AtomicInteger(0);
        this.dingQueue = new AtomicInteger(0);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (dingQueue.getAndUpdate(i -> i > 0 ? --i : i) > 0) {
                runnable.run();
            }
        }
    }

    @Override
    public void increment() {
        counter.getAndIncrement();
        if (counter.compareAndSet(period, 0)) {
            dingQueue.getAndIncrement();
        }
    }
}
