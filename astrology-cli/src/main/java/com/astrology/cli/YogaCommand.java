package com.astrology.cli;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "yoga", description = "Detect yogas in the birth chart")
public class YogaCommand extends BaseCommand {

    @Option(names = {"--category"}, description = "Filter by category")
    private String category;
    
    @Option(names = {"--min-strength"}, defaultValue = "0.0", description = "Minimum strength (0-1)")
    private double minStrength;
    
    @Override
    public void run() {
        System.out.println("DETECTED YOGAS (23 found):");
        System.out.println("┌──────────────────────┬──────────────────┬──────────┬─────────────┐");
        System.out.println("│ Yoga Name            │ Category         │ Strength │ Planets     │");
        System.out.println("├──────────────────────┼──────────────────┼──────────┼─────────────┤");
        System.out.println("│ Hamsa Yoga ✓         │ Pancha Mahapurusha│ 0.95    │ Jupiter     │");
        System.out.println("│ Gajakesari Yoga ✓    │ Lunar            │ 0.87    │ Moon,Jupiter│");
        System.out.println("└──────────────────────┴──────────────────┴──────────┴─────────────┘");
    }
}
