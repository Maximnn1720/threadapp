package ru.study.application.Logger;

import java.io.PrintStream;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class LogWriter {
    private static final Integer CAPACITY = 1000;
    private final BlockingQueue<String> queue;
    private final LoggerThread loggerThread;

    public LogWriter(PrintStream writer) {
        this.queue = new LinkedBlockingDeque<>(CAPACITY);
        this.loggerThread = new LoggerThread(writer);
    }

    public void start() {
        loggerThread.start();
    }

    public void stop() {
        loggerThread.interrupt();
    }

    public void log(String text) {
        try {
            queue.put(String.format("logWriter %s:    %s", LocalTime.from(Instant.now().atZone(ZoneId.of("GMT+3"))), text));
        } catch (InterruptedException ignored) {
        }
    }

    private class LoggerThread extends Thread {
        private final PrintStream printWriter;

        private LoggerThread(PrintStream printWriter) {
            this.printWriter = printWriter;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    printWriter.println(queue.take());
                }
            } catch (InterruptedException ignored) {
            } finally {
                printWriter.close();
            }
        }
    }
}
