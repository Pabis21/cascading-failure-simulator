package org.example.engine;

import org.example.model.MicroserviceGraph;
import org.example.model.Service;

import java.io.FileWriter;
import java.io.PrintWriter;

public class ExportResults {

    public static void exportResults(MicroserviceGraph graph, int iterations) throws Exception {

        PrintWriter writer = new PrintWriter(new FileWriter("simulation_results.csv"));

        writer.println("service,direct_failures,cascaded_failures,total_failures,lambda_observed,mttr_observed");

        for (Service s : graph.services) {

            double lambdaObserved = (double) s.totalFailures / iterations;

            double mttrObserved =
                    s.totalFailures == 0 ? 0 :
                            (double) s.totalDowntime / s.totalFailures;

            writer.println(
                    s.name + "," +
                            s.directFailures + "," +
                            s.cascadedFailures + "," +
                            s.totalFailures + "," +
                            lambdaObserved + "," +
                            mttrObserved
            );
        }

        writer.close();

        System.out.println("Simulation results exported to simulation_results.csv");
    }
}
