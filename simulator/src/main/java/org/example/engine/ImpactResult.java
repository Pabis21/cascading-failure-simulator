package org.example.engine;

public class ImpactResult {

    public String serviceName;
    public double rawImpact;
    public double expectedImpact;
    public double fsr;

    public ImpactResult(String serviceName, double rawImpact, double expectedImpact, double fsr) {
        this.serviceName = serviceName;
        this.rawImpact = rawImpact;
        this.expectedImpact = expectedImpact;
        this.fsr = fsr;
    }
}