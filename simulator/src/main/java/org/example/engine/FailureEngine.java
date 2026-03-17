package org.example.engine;

import org.example.model.Edge;
import org.example.model.MicroserviceGraph;
import org.example.model.Service;

import java.util.*;

public class FailureEngine {

    private MicroserviceGraph graph;
    private Random random = new Random();

    public FailureEngine(MicroserviceGraph graph) {
        this.graph = graph;
    }


    public void simulate(int iterations) {

        for (int i = 0; i < iterations; i++) {

            Set<Service> visited = new HashSet<>();

            for (Service s : graph.services) {

                if (random.nextDouble() < s.failureRate) {

                    s.directFailures++;
                    s.totalFailures++;
                    s.totalDowntime += s.mttr;

                    // root failure → mark as NOT cascaded
                    propagateFailure(s, visited, true);
                }
            }
        }
    }


    public void propagateFailure(Service service, Set<Service> visited, boolean isRoot) {

        if (visited.contains(service)) return;

        visited.add(service);

        // Only count cascaded if NOT root
        if (!isRoot) {
            service.cascadedFailures++;
            service.totalFailures++;
            service.totalDowntime += service.mttr;
        }

        // Reverse dependency propagation
        for (Edge e : graph.getDependents(service)) {

            Service dependent = e.target;

            if (!visited.contains(dependent)) {

                if (random.nextDouble() < e.propagationProbability) {
                    propagateFailure(dependent, visited, false);
                }
            }
        }
    }

}
