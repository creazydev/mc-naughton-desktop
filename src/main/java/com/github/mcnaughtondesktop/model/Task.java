package com.github.mcnaughtondesktop.model;

public class Task {
    private final int id;
    private int duration;
    private int startAt;

    public Task(int id, int duration) {
        this.id = id;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public int getDuration() {
        return duration;
    }

    public int getStartAt() {
        return startAt;
    }

    public void setStartAt(int startAt) {
        this.startAt = startAt;
    }

    public Task splitAt(int firstPartDuration) {
        int oldDuration = duration;
        duration = firstPartDuration;
        return new Task(id, oldDuration - firstPartDuration);
    }

}
