package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class MicroserviceGraph {

    public List<Service> services = new ArrayList<>();

    public void addService(Service s) {
        services.add(s);
    }

    // Add dependency edge
    public void addDependency(Service from, Service to, double probability) {
        from.dependencies.add(new Edge(to, probability));
    }

    // Find service by name
    public Service getService(String name) {
        for (Service s : services) {
            if (s.name.equals(name)) {
                return s;
            }
        }
        return null;
    }

    // Return services that depend on the given service
    public List<Service> getDependents(Service service) {

        List<Service> dependents = new ArrayList<>();

        for (Service s : services) {
            for (Edge e : s.dependencies) {

                if (e.target.equals(service)) {
                    dependents.add(s);
                }

            }
        }

        return dependents;
    }

}
