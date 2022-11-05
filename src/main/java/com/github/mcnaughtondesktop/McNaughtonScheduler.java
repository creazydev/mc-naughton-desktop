package com.github.mcnaughtondesktop;

import com.github.mcnaughtondesktop.model.Machine;
import com.github.mcnaughtondesktop.model.Task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class McNaughtonScheduler {
    private final double cmax;
    private final int machineCount;
    private final List<Task> tasks;

    public McNaughtonScheduler(double cmax, int machineCount, List<Task> tasks) {
        this.cmax = cmax;
        this.machineCount = machineCount;
        this.tasks = tasks;
    }

    public List<Machine> schedule() {
        int currentId = 1;
        List<Machine> result = new ArrayList<>();
        Task toAddToNext = null;
        Iterator<Task> taskIterator = this.tasks.iterator();

        for (int i = 0; i < machineCount; i++) {
            Machine machine = new Machine(currentId++);
            int durationSum = 0;

            if (toAddToNext != null) {
                machine.addTask(toAddToNext);
                durationSum += toAddToNext.getDuration();
                toAddToNext = null;
            };

            while (taskIterator.hasNext()) {
                Task nextTask = taskIterator.next();

                if (durationSum == cmax) {
                    toAddToNext = nextTask;
                    break;
                }

                if (durationSum + nextTask.getDuration() > cmax) {
                    toAddToNext = nextTask.splitAt(cmax - durationSum);
                    machine.addTask(nextTask);
                    break;
                }

                machine.addTask(nextTask);
                durationSum += nextTask.getDuration();
            }
            result.add(machine);
        }

        return result;
    }
}
