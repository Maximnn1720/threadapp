package ru.study.application;

import ru.study.application.Logger.LogWriter;
import ru.study.application.TimerTask.TimerPlaner;
import ru.study.application.TimerTask.TimerTask;

public class TimerTaskApp {
    public static void main(String[] args) {
        LogWriter logWriter = new LogWriter(System.out);
        TimerPlaner timerPlaner = new TimerPlaner(logWriter);

        TimerTask everySecondTask = new TimerTask(logWriter) {
            @Override
            public void run() {
                logWriter.log("Пишу 1");
            }
        };

        TimerTask everyTwoSecondTask = new TimerTask(logWriter) {
            @Override
            public void run() {
                logWriter.log("Пишу 2");
            }
        };

        TimerTask everyFiveSecondTask = new TimerTask(logWriter) {
            @Override
            public void run() {
                logWriter.log("Пишу 5");
            }
        };

        timerPlaner.scheduleWork(5, everyFiveSecondTask);
        timerPlaner.scheduleWork(2, everyTwoSecondTask);
        timerPlaner.scheduleWork(1, everySecondTask);

        logWriter.start();
        timerPlaner.start();
        try {
            Thread.sleep(10100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timerPlaner.stopTimer();
        logWriter.stop();
    }
}
