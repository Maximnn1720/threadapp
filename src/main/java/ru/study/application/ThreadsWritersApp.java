package ru.study.application;

import ru.study.application.Logger.LogWriter;
import ru.study.application.Utils.ArraysGenerator;
import ru.study.application.WriterThreads.NumbersWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadsWritersApp {
    public static void main(String[] args) {
        LogWriter logger = new LogWriter(System.out);
        logger.start();
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Callable<Object>> tasks = new ArrayList<>();
        tasks.add(Executors.callable(new NumbersWriter(ArraysGenerator.getListOfNumbers(5000, 10000), "file1.txt", logger)));
        tasks.add(Executors.callable(new NumbersWriter(ArraysGenerator.getListOfNumbers(5000000, 10000), "file2.txt", logger)));
        tasks.add(Executors.callable(new NumbersWriter(ArraysGenerator.getListOfNumbers(500000, 10000), "file3.txt", logger)));
        try {
            executor.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
            logger.log("Конец главного потока");
            logger.stop();
        }
    }
}
