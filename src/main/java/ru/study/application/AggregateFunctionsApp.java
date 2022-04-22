package ru.study.application;

import ru.study.application.AggregateFunctions.GetMaxFunction;
import ru.study.application.AggregateFunctions.GetMinFunction;
import ru.study.application.Utils.ArraysGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class AggregateFunctionsApp {
    public static void main(String[] args) {
        List<Integer> numbers = ArraysGenerator.getListOfNumbers(5000, 10000);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        List<Callable<Integer>> tasks = Arrays.asList(new GetMaxFunction(numbers), new GetMinFunction(numbers));
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(executor);
        for (var callTask :
                tasks) {
            completionService.submit(callTask);
        }

        for (int i = tasks.size(); i > 0; i--) {
            try {
                Future<Integer> future = completionService.take();
                System.out.println(future.get());
                future.cancel(true);
            } catch (InterruptedException | ExecutionException ex) {
            }
        }
        executor.shutdownNow();
    }
}
