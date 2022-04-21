package ru.study.application.AggregateFunctions;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

public class GetMinFunction implements Callable<Integer> {
    private final List<Integer> numbers;

    public GetMinFunction(List<Integer> numbers) {
        this.numbers = numbers;
    }

    @Override
    public Integer call() {
        return Collections.min(numbers);
    }
}
