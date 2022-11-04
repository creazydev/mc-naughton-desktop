package com.github.mcnaughtondesktop.model;

public class Task {
    private final int id;
    private final int startAt;
    private final int duration;

    public Task(int id, int startAt, int duration) {
        this.id = id;
        this.startAt = startAt;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public int getStartAt() {
        return startAt;
    }

    public int getDuration() {
        return duration;
    }
}
