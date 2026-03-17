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

                // Step 1: direct failure
                if (random.nextDouble() < s.failureRate) {

                    s.directFailures++; // track direct failures

                    propagateFailure(s, visited, false); // false = not cascaded
                }
            }
        }
    }


    private void propagateFailure(Service service, Set<Service> visited, boolean cascaded) {

        if (visited.contains(service))
            return;

        visited.add(service);

        service.totalFailures++;
        service.totalDowntime += service.mttr;

        if (cascaded)
            service.cascadedFailures++;

        for (Edge e : service.dependencies) {

            if (random.nextDouble() < e.propagationProbability) {

                propagateFailure(e.target, visited, true);

            }
        }
    }

}
