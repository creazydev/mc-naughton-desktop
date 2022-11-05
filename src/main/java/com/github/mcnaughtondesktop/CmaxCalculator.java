package com.github.mcnaughtondesktop;

import com.github.mcnaughtondesktop.model.Task;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public class CmaxCalculator {
    private static final String TEMPLATE = "Cmax = max{max{%s}, %s / %s}} = max{%s, %s} = %s";
    private final List<Task> tasks;
    private final int machineCount;
    private double result;
    private String resultString;

    public CmaxCalculator(int machineCount, List<Task> tasks) {
        this.machineCount = machineCount;
        this.tasks = tasks;
        calculate();
    }

    private void calculate() {
        double taskExecutionTimesSum = tasks.stream().mapToDouble(Task::getDuration).sum();
        double maxTaskDuration = tasks.stream().mapToDouble(Task::getDuration).reduce(Double::max).orElseThrow();
        double averageDurationPerMachine = tasks.stream().mapToDouble(Task::getDuration).sum() / machineCount;
        result = Double.max(maxTaskDuration, averageDurationPerMachine);

        String taskExecutionTimesString = tasks.stream().map(Task::getDuration).collect(Collectors.toList()).toString();
        String taskExecutionTimesStringTrimmed = taskExecutionTimesString.substring(1, taskExecutionTimesString.length() - 1);

        resultString = String.format(
            TEMPLATE,
            taskExecutionTimesStringTrimmed,
            this.setPrecision(taskExecutionTimesSum, 2),
            machineCount,
            this.setPrecision(maxTaskDuration, 2),
            this.setPrecision(averageDurationPerMachine, 2),
            this.setPrecision(result, 2)
        );
    }

    public double getResult() {
        return result;
    }

    public String getResultString() {
        return resultString;
    }

    private BigDecimal setPrecision(double v, int p) {
        return new BigDecimal(v).setScale(p, RoundingMode.FLOOR);
    }
}
