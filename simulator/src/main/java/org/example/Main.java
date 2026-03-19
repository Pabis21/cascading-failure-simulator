package org.example;

import org.example.engine.CSVLoader;
import org.example.engine.ExportImpactResults;
import org.example.engine.ImpactCalculator;
import org.example.engine.ImpactResult;
import org.example.model.MicroserviceGraph;

import java.util.List;

public class Main {

    static class Scenario {
        String name;
        String servicesFile;
        String dependenciesFile;
        String outputFile;

        Scenario(String name, String servicesFile, String dependenciesFile, String outputFile) {
            this.name = name;
            this.servicesFile = servicesFile;
            this.dependenciesFile = dependenciesFile;
            this.outputFile = outputFile;
        }
    }

    public static void main(String[] args) throws Exception {

        Scenario[] scenarios = new Scenario[] {
                new Scenario(
                        "Baseline",
                        "services_baseline.csv",
                        "dependencies_baseline.csv",
                        "output/impact_baseline.csv"
                ),
                new Scenario(
                        "Low Probability",
                        "services_baseline.csv",
                        "dependencies_low_p.csv",
                        "output/impact_low_p.csv"
                ),
                new Scenario(
                        "High Probability",
                        "services_baseline.csv",
                        "dependencies_high_p.csv",
                        "output/impact_high_p.csv"
                ),
                new Scenario(
                        "High MTTR",
                        "services_high_mttr.csv",
                        "dependencies_baseline.csv",
                        "output/impact_high_mttr.csv"
                ),
                new Scenario(
                        "High Failure Rate",
                        "services_high_failure.csv",
                        "dependencies_baseline.csv",
                        "output/impact_high_failure.csv"
                )
        };

        for (Scenario scenario : scenarios) {
            System.out.println("\n==============================");
            System.out.println("Running scenario: " + scenario.name);
            System.out.println("Services: " + scenario.servicesFile);
            System.out.println("Dependencies: " + scenario.dependenciesFile);
            System.out.println("Output: " + scenario.outputFile);
            System.out.println("==============================");

            MicroserviceGraph graph = new MicroserviceGraph();

            CSVLoader.loadServices(scenario.servicesFile, graph);
            CSVLoader.loadDependencies(scenario.dependenciesFile, graph);

            ImpactCalculator calculator = new ImpactCalculator(graph);
            List<ImpactResult> results = calculator.calculateAllImpacts();

            System.out.println("Results count: " + results.size());

            ExportImpactResults.export(results, scenario.outputFile);

            double svi = calculator.calculateSVI(results);
            ImpactResult max = calculator.getMaxRiskService(results);

            System.out.println("\n--- IMPACT RESULTS (" + scenario.name + ") ---");
            for (ImpactResult r : results) {
                System.out.println(
                        r.serviceName +
                                " | Raw: " + r.rawImpact +
                                " | Expected: " + r.expectedImpact +
                                " | FSR: " + r.fsr
                );
            }

            System.out.println("\nSystem Vulnerability Index (SVI): " + svi);
            System.out.println("Max Risk Service: " + max.serviceName + " (" + max.expectedImpact + ")");
        }

        System.out.println("\nAll scenarios completed.");
    }
}