package com.github.mcnaughtondesktop.model;

public class Task {
    private final int id;
    private double duration;
    public Task(int id, double duration) {
        this.id = id;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public double getDuration() {
        return duration;
    }


    public Task splitAt(double firstPartDuration) {
        double oldDuration = duration;
        duration = firstPartDuration;
        return new Task(id, oldDuration - firstPartDuration);
    }

}
