package org.example.engine;

import org.example.model.Edge;
import org.example.model.MicroserviceGraph;
import org.example.model.Service;

import java.util.Random;

public class FailureEngine {
    MicroserviceGraph graph;
    Random random = new Random();

    public FailureEngine(MicroserviceGraph graph) {
        this.graph = graph;
    }

    public void simulate(int totalTime) {
        for (int t = 0; t < totalTime; t++) {

            // Random Failures
            for (Service s : graph.services) {
                if (!s.isFailed && random.nextDouble() < s.failureRate) {
                    failService(s, t);
                }
            }

            // Propagation
            propagateFailures(t);

            //  Recovery
            updateRecovery(t);
        }
    }

    void failService(Service s, int time) {
        s.isFailed = true;
        s.failureStartTime = time;
        s.totalFailures++;
        System.out.println("FAIL: " + s.name + " at time " + time);
    }

    void propagateFailures(int time) {
        for (Service s : graph.services) {
            if (s.isFailed) {
                for (Edge e : s.dependencies) {
                    if (!e.target.isFailed &&
                            random.nextDouble() < e.propagationProbability) {
                        failService(e.target, time);
                    }
                }
            }
        }
    }

    void updateRecovery(int time) {
        for (Service s : graph.services) {
            if (s.isFailed &&
                    (time - s.failureStartTime) >= s.mttr) {

                s.isFailed = false;
                s.totalDowntime += s.mttr;
                System.out.println("RECOVER: " + s.name + " at time " + time);
            }
        }
    }
}
