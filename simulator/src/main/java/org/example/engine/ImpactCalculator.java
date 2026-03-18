package org.example.engine;

import org.example.model.Edge;
import org.example.model.MicroserviceGraph;
import org.example.model.Service;

import java.util.*;

public class ImpactCalculator {

    private MicroserviceGraph graph;

    public ImpactCalculator(MicroserviceGraph graph) {
        this.graph = graph;
    }

    public ImpactResult calculateImpact(Service source) {

        Map<Service, Double> probabilities = new HashMap<>();
        Queue<Service> queue = new LinkedList<>();

        probabilities.put(source, 1.0);
        queue.add(source);

        double rawImpact = 0.0;
        Set<Service> reachable = new HashSet<>();

        while (!queue.isEmpty()) {

            Service current = queue.poll();
            double currentProb = probabilities.get(current);

            for (Edge e : graph.getDependents(current)) {

                Service next = e.target;

                double newProb = currentProb * e.propagationProbability;

                if (!probabilities.containsKey(next) || newProb > probabilities.get(next)) {
                    probabilities.put(next, newProb);
                    queue.add(next);
                }
            }
        }

        for (Map.Entry<Service, Double> entry : probabilities.entrySet()) {

            Service s = entry.getKey();

            if (!s.equals(source)) {
                double prob = entry.getValue();
                rawImpact += prob * s.mttr;
                reachable.add(s);
            }
        }

        double expectedImpact = source.failureRate * rawImpact;

        double fsr = (double) reachable.size() / (graph.services.size() - 1);

        return new ImpactResult(source.name, rawImpact, expectedImpact, fsr);
    }

    public List<ImpactResult> calculateAllImpacts() {

        List<ImpactResult> results = new ArrayList<>();

        for (Service s : graph.services) {
            results.add(calculateImpact(s));
        }

        return results;
    }

    public double calculateSVI(List<ImpactResult> results) {
        double sum = 0;
        for (ImpactResult r : results) {
            sum += r.expectedImpact;
        }
        return sum / results.size();
    }

    public ImpactResult getMaxRiskService(List<ImpactResult> results) {

        ImpactResult max = results.get(0);

        for (ImpactResult r : results) {
            if (r.expectedImpact > max.expectedImpact) {
                max = r;
            }
        }

        return max;
    }
}