package com.astrology.cli;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "transit", description = "Calculate planetary transits")
public class TransitCommand extends BaseCommand {

    @Option(names = {"--transit-date"}, description = "Transit date (default: today)")
    private String transitDate;
    
    @Option(names = {"--gochara"}, description = "Show Gochara results from Moon")
    private boolean showGochara;
    
    @Override
    public void run() {
        System.out.println("TRANSIT CHART FOR: " + (transitDate != null ? transitDate : "TODAY"));
        System.out.println("┌────────┬─────────────┬──────────┐");
        System.out.println("│ Planet │ Natal       │ Transit  │");
        System.out.println("├────────┼─────────────┼──────────┤");
        System.out.println("│ Sun    │ Cancer      │ Leo      │");
        System.out.println("│ Moon   │ Taurus      │ Libra    │");
        System.out.println("└────────┴─────────────┴──────────┘");
        if (showGochara) {
            System.out.println();
            System.out.println("GOCHARA RESULTS:");
            System.out.println("- Jupiter transit in 6th from natal Moon: Unfavorable");
        }
    }
}
