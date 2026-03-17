package org.example;

import org.example.engine.CSVLoader;
import org.example.engine.FailureEngine;
import org.example.model.MicroserviceGraph;
import org.example.model.Service;
import static org.example.engine.ExportResults.exportResults;

public class Main {
    public static void main(String[] args) throws Exception {
        MicroserviceGraph graph = new MicroserviceGraph();

        CSVLoader.loadServices("services.csv", graph);
        CSVLoader.loadDependencies("dependencies.csv", graph);

        FailureEngine engine = new FailureEngine(graph);
        engine.simulate(1000);


        exportResults(graph, 1000);
        System.out.println("\n--- RESULTS ---");

        for (Service s : graph.services) {

            double lambdaObserved = (double) s.totalFailures / 1000;

            double mttrObserved =
                    s.totalFailures == 0 ? 0 :
                            (double) s.totalDowntime / s.totalFailures;

            System.out.println(
                    s.name +
                            " | Direct: " + s.directFailures +
                            " | Cascaded: " + s.cascadedFailures +
                            " | Total: " + s.totalFailures +
                            " | λ_obs: " + lambdaObserved +
                            " | MTTR_obs: " + mttrObserved
            );
        }

    }
}