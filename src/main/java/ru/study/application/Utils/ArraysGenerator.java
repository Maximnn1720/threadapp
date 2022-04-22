package ru.study.application.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArraysGenerator {
    private static final Random rdm = new Random(System.currentTimeMillis());
    public static List<Integer> getListOfNumbers(int capacity, int range){
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            numbers.add(rdm.nextInt(range));
        }
        return numbers;
    }
}
