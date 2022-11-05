package com.github.mcnaughtondesktop.model;

import java.util.List;

public class McNaughtonResult {
    private final Integer cmax;
    private final List<Machine> machines;

    public McNaughtonResult(Integer cmax, List<Machine> machines) {
        this.cmax = cmax;
        this.machines = machines;
    }

    public Integer getCmax() {
        return cmax;
    }

    public List<Machine> getMachines() {
        return machines;
    }
}
