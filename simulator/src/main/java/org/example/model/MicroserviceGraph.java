package org.example.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MicroserviceGraph {

    public List<Service> services = new ArrayList<>();

    // Reverse dependencies: who depends on me
    private Map<Service, List<Edge>> reverseDependencies = new HashMap<>();

    public void addService(Service s) {
        services.add(s);
    }

    public Service getService(String name) {
        for (Service s : services) {
            if (s.name.equals(name)) {
                return s;
            }
        }
        return null;
    }

    public void addDependency(Service source, Service target, double probability) {

        // forward (optional)
        source.dependencies.add(new Edge(target, probability));

        // reverse (THIS is what matters)
        reverseDependencies
                .computeIfAbsent(target, k -> new ArrayList<>())
                .add(new Edge(source, probability));
    }

    // THIS is what we will use for propagation
    public List<Edge> getDependents(Service service) {
        return reverseDependencies.getOrDefault(service, new ArrayList<>());
    }

}
