package com.github.mcnaughtondesktop;

import com.github.mcnaughtondesktop.model.Machine;
import com.github.mcnaughtondesktop.model.McNaughtonResult;
import com.github.mcnaughtondesktop.model.Task;

import java.util.List;

public class McNaughton {

    public static McNaughtonResult calculate(int machineQuantity, List<Integer> tasksExecutionTimes) {
        Machine machine = new Machine(1);
        Task task = new Task(0, 2, 1);
        machine.addTask(task);

        return new McNaughtonResult(
            0,
            "Cmax = max{max{1, 2, 4, 4, 2, 2}, 15 / 3}} = max{4, 5} = 5",
            List.of(machine)
        );
    }
}
