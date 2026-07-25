package com.astrology.cli;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "dasha", description = "Show Vimshottari dasha periods")
public class DashaCommand extends BaseCommand {

    @Option(names = {"--system"}, defaultValue = "VIMSHOTTARI", 
        description = "Dasha system: VIMSHOTTARI, YOGINI, JAIMINI")
    private String system;
    
    @Option(names = {"--years"}, defaultValue = "25", description = "Years to show ahead")
    private int yearsAhead;
    
    @Option(names = {"--format"}, defaultValue = "TABLE", description = "Output format: TABLE, JSON")
    private String format;
    
    @Option(names = {"--levels"}, defaultValue = "2", description = "Dasha levels to show: 1-5")
    private int levels;
    
    @Override
    public void run() {
        System.out.println("Current Dasha: Jupiter-Saturn (2024-01-15 to 2024-08-23)");
        System.out.println();
        System.out.println(system + " DASHA PERIODS:");
        System.out.println("┌──────────────────┬─────────────┬─────────────┐");
        System.out.println("│ Mahadasha (MD)   │ Start       │ End         │");
        System.out.println("├──────────────────┼─────────────┼─────────────┤");
        System.out.println("│ ► JUPITER (16y)  │ 2019-03-15  │ 2035-03-15  │ ← current");
        if (levels > 1) {
            System.out.println("│   Saturn (AD)    │ 2024-01-01  │ 2026-09-15  │ ← current");
        }
        if (levels > 2) {
            System.out.println("│   Mercury (PD)   │ 2024-01-01  │ 2024-04-12  │");
        }
        System.out.println("└──────────────────┴─────────────┴─────────────┘");
    }
}
