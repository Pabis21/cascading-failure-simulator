package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Service {

    public String name;
    public double failureRate;
    public int mttr;

    public int totalFailures = 0;
    public int totalDowntime = 0;

    public int directFailures = 0;
    public int cascadedFailures = 0;

    public List<Edge> dependencies = new ArrayList<>();

    public Service(String name, double failureRate, int mttr) {
        this.name = name;
        this.failureRate = failureRate;
        this.mttr = mttr;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Service)) return false;
        Service s = (Service) o;
        return name.equals(s.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

