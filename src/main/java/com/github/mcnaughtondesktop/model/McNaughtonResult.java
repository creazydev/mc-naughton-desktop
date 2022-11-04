package com.github.mcnaughtondesktop.model;

import java.util.List;

public class McNaughtonResult {
    private final Integer cmax;
    private final String cmaxPattern;
    private final List<Machine> machines;

    public McNaughtonResult(Integer cmax, String cmaxPattern, List<Machine> machines) {
        this.cmax = cmax;
        this.cmaxPattern = cmaxPattern;
        this.machines = machines;
    }

    public Integer getCmax() {
        return cmax;
    }

    public String getCmaxPattern() {
        return cmaxPattern;
    }

    public List<Machine> getMachines() {
        return machines;
    }
}
