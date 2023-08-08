package ru.javaops.basejava.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamNumbers {
    public int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce((a, b) -> a * 10 + b)
                .orElseThrow();
    }
    public List<Integer> oddOrEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> map = integers.stream()
                .collect(Collectors.partitioningBy(i -> i % 2 == 0));
        return map.get(map.get(false).size() % 2 != 0);
    }
}
