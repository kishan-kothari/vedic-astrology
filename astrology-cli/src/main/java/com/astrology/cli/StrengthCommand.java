package com.astrology.cli;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "strength", description = "Calculate planetary strengths")
public class StrengthCommand extends BaseCommand {

    @Option(names = {"--type"}, defaultValue = "SHADBALA", 
        description = "Type: SHADBALA, ASHTAKAVARGA, ALL")
    private String type;
    
    @Override
    public void run() {
        System.out.println("PLANETARY STRENGTHS (" + type + "):");
        System.out.println("┌────────┬─────────────┬──────────┐");
        System.out.println("│ Planet │ Strength    │ Status   │");
        System.out.println("├────────┼─────────────┼──────────┤");
        System.out.println("│ Sun    │ " + ConsoleRenderer.GREEN + "1.42 Rupas  " + ConsoleRenderer.RESET + "│ Strong   │");
        System.out.println("│ Moon   │ " + ConsoleRenderer.RED + "0.85 Rupas  " + ConsoleRenderer.RESET + "│ Weak     │");
        System.out.println("│ Mars   │ " + ConsoleRenderer.GOLD + "1.10 Rupas  " + ConsoleRenderer.RESET + "│ Average  │");
        System.out.println("└────────┴─────────────┴──────────┘");
    }
}
