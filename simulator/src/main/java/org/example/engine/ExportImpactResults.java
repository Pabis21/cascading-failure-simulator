package org.example.engine;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

public class ExportImpactResults {

    public static void export(List<ImpactResult> results, String fileName) throws Exception {
        System.out.println("Writing file: " + fileName);

        File outFile = new File(fileName);
        File parent = outFile.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        PrintWriter writer = new PrintWriter(outFile);

        writer.println("service,raw,expected,fsr");

        for (ImpactResult r : results) {
            writer.println(
                    r.serviceName + "," +
                            r.rawImpact + "," +
                            r.expectedImpact + "," +
                            r.fsr
            );
        }

        writer.close();
        System.out.println("Export complete: " + outFile.getAbsolutePath());
    }
}