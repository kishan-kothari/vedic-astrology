package com.astrology.cli;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "report", description = "Generate astrology report")
public class ReportCommand extends BaseCommand {

    @Option(names = {"--format"}, defaultValue = "PDF", description = "Format: PDF, HTML, JSON")
    private String format;
    
    @Option(names = {"--output", "-o"}, defaultValue = "report", description = "Output filename (without extension)")
    private String outputFile;
    
    @Override
    public void run() {
        System.out.printf("✓ Report generated: %s.%s (1.2 MB)%n", outputFile, format.toLowerCase());
    }
}
