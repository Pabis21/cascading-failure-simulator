package org.example;

import org.example.engine.FailureEngine;
import org.example.model.MicroserviceGraph;
import org.example.model.Service;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        MicroserviceGraph graph = new MicroserviceGraph();

        Service A = new Service("A", 0.02, 5);
        Service B = new Service("B", 0.01, 4);
        Service C = new Service("C", 0.015, 6);
        Service D = new Service("D", 0.005, 3);

        graph.addService(A);
        graph.addService(B);
        graph.addService(C);
        graph.addService(D);

        graph.addDependency(A, B, 0.6);
        graph.addDependency(A, C, 0.4);
        graph.addDependency(B, D, 0.5);
        graph.addDependency(C, D, 0.7);

        FailureEngine engine = new FailureEngine(graph);
        engine.simulate(1000);

        System.out.println("\n--- RESULTS ---");
        for (Service s : graph.services) {
            double lambda = (double)s.totalFailures / 1000;
            double mttrObserved = s.totalFailures == 0 ? 0 :
                    (double)s.totalDowntime / s.totalFailures;

            System.out.println(s.name +
                    " | Failures: " + s.totalFailures +
                    " | λ: " + lambda +
                    " | MTTR: " + mttrObserved);
        }
    }
}