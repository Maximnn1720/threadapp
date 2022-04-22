package ru.study.application.WriterThreads;

import ru.study.application.Logger.LogWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class NumbersWriter implements Runnable {

    private final LogWriter logger;
    private final List<Integer> numbers;
    private final String fileName;

    public NumbersWriter(List<Integer> numbers, String fileName, LogWriter logger) {
        this.numbers = numbers;
        this.fileName = fileName;
        this.logger = logger;
    }

    @Override
    public void run() {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            String text = numbers.stream().map(Object::toString).collect(Collectors.joining(" "));
//            Thread.currentThread().sleep(new Random().nextInt(10000));
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
        }
        logger.log(String.format("Файл %s записан", fileName));
    }
}
