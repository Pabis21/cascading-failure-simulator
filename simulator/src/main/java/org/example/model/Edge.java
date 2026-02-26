package org.example.model;

public class Edge {
    public Service target;
    public double propagationProbability;

    Edge(Service target, double propagationProbability) {
        this.target = target;
        this.propagationProbability = propagationProbability;
    }
}
