package com.github.mcnaughtondesktop;

import com.github.mcnaughtondesktop.model.Task;

import java.util.List;
import java.util.stream.Collectors;

public class CmaxCalculator {
    private static final String TEMPLATE = "Cmax = max{max{%s}, %s / %s}} = max{%s, %s} = %s";
    private final List<Task> tasks;
    private final int machineCount;
    private int result;
    private String resultString;

    public CmaxCalculator(int machineCount, List<Task> tasks) {
        this.machineCount = machineCount;
        this.tasks = tasks;
        calculate();
    }

    private void calculate() {
        int taskExecutionTimesSum = tasks.stream().mapToInt(Task::getDuration).sum();
        int maxTaskDuration = tasks.stream().mapToInt(Task::getDuration).reduce(Integer::max).orElseThrow();
        int averageDurationPerMachine = tasks.stream().mapToInt(Task::getDuration).sum() / machineCount;
        result = Integer.max(maxTaskDuration, averageDurationPerMachine);

        String taskExecutionTimesString = tasks.stream().map(Task::getDuration).collect(Collectors.toList()).toString();
        String taskExecutionTimesStringTrimmed = taskExecutionTimesString.substring(1, taskExecutionTimesString.length() - 1);
        resultString = String.format(TEMPLATE, taskExecutionTimesStringTrimmed, taskExecutionTimesSum, machineCount, maxTaskDuration, averageDurationPerMachine, result);
    }

    public int getResult() {
        return result;
    }

    public String getResultString() {
        return resultString;
    }
}
