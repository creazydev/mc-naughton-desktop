package com.github.mcnaughtondesktop.model;

import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Machine {
    private final int id;
    private final List<Task> tasks = new ArrayList<>();

    public Machine(int id) {
        this.id = id;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public int getId() {
        return id;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        return "Maszyna " + this.id;
    }
}
