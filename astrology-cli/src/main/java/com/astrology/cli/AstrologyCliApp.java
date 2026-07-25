package com.astrology.cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
    name = "astro",
    mixinStandardHelpOptions = true,
    version = "Vedic Astrology Engine 1.0.0",
    description = "Production-quality Vedic Astrology Engine CLI",
    subcommands = {
        ChartCommand.class,
        DashaCommand.class,
        YogaCommand.class,
        StrengthCommand.class,
        TransitCommand.class,
        ReportCommand.class
    }
)
public class AstrologyCliApp implements Runnable {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new AstrologyCliApp()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        new CommandLine(this).usage(System.out);
    }
}
