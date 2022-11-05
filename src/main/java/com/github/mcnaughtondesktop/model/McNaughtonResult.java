package com.github.mcnaughtondesktop.model;

import java.util.List;

public class McNaughtonResult {
    private final Double cmax;
    private final List<Machine> machines;

    public McNaughtonResult(Double cmax, List<Machine> machines) {
        this.cmax = cmax;
        this.machines = machines;
    }

    public Double getCmax() {
        return cmax;
    }

    public List<Machine> getMachines() {
        return machines;
    }
}
