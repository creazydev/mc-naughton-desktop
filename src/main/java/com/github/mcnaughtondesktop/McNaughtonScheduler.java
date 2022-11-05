package com.github.mcnaughtondesktop;

import com.github.mcnaughtondesktop.model.Machine;
import com.github.mcnaughtondesktop.model.Task;

import java.util.ArrayList;
import java.util.List;

public class McNaughtonScheduler {
    private final int cmax;
    private final int machineCount;
    private final List<Task> tasks;

    public McNaughtonScheduler(int cmax, int machineCount, List<Task> tasks) {
        this.cmax = cmax;
        this.machineCount = machineCount;
        this.tasks = tasks;
    }

    public List<Machine> schedule() {
        int currentId = 1;
        List<Machine> result = new ArrayList<>();
        for (int i = 0; i < machineCount; i++) {
            Machine machine = new Machine(currentId++);

        }
        // @TODO
        return new ArrayList<>();
    }
}
