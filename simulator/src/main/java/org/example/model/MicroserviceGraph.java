package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class MicroserviceGraph {
    public List<Service> services = new ArrayList<>();

    public void addService(Service s) {
        services.add(s);
    }

    public void addDependency(Service from, Service to, double probability) {
        from.dependencies.add(new Edge(to, probability));
    }
}
