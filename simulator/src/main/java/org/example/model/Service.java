package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Service {
    public String name;
    public double failureRate;     // λ
    public double mttr;            // Mean time to recovery
    public boolean isFailed = false;
    public int failureStartTime = -1;

    public List<Edge> dependencies = new ArrayList<>();

    // Statistics
    public int totalFailures = 0;
    public int totalDowntime = 0;

    public Service(String name, double failureRate, double mttr) {
        this.name = name;
        this.failureRate = failureRate;
        this.mttr = mttr;
    }
}
