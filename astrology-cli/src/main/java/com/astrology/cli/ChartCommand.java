package com.astrology.cli;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import com.astrology.planets.BirthData;
import com.astrology.core.EphemerisConfig;
// Note: Assuming a ChartEngine or similar exists to compute the chart, we will print a placeholder for now
// to satisfy the structure if we don't have the exact API, or we can use ConsoleRenderer.

@Command(name = "chart", description = "Display natal birth chart")
public class ChartCommand extends BaseCommand {

    @Option(names = {"--style"}, defaultValue = "TABLE", description = "Output style: TABLE, NORTH, SOUTH")
    private String style;

    @Override
    public void run() {
        BirthData birthData = buildBirthData();
        EphemerisConfig config = buildConfig();

        try (com.astrology.core.SwissEphemerisService sweService = new com.astrology.core.SwissEphemerisService(config)) {
            com.astrology.planets.PlanetaryCalculator calculator = new com.astrology.planets.PlanetaryCalculator(config);
            com.astrology.planets.BirthChart chart = calculator.calculateBirthChart(birthData);

            ConsoleRenderer.printHeader("VEDIC ASTROLOGY BIRTH CHART");
            System.out.printf("%s║ Name: %-37s║%s%n", ConsoleRenderer.GOLD, name, ConsoleRenderer.RESET);
            System.out.printf("%s║ Date: %s, %s %-16s║%s%n", ConsoleRenderer.GOLD, date, time, timezone, ConsoleRenderer.RESET);
            System.out.printf("%s║ Location: %.4f, %.4f                ║%s%n", ConsoleRenderer.GOLD, latitude, longitude, ConsoleRenderer.RESET);
            System.out.printf("%s║ Ayanamsha: %-25s║%s%n", ConsoleRenderer.GOLD, ayanamsha, ConsoleRenderer.RESET);
            System.out.printf("%s╠══════════════════════════════════════════╣%s%n", ConsoleRenderer.GOLD, ConsoleRenderer.RESET);

            System.out.printf("║ ASCENDANT: %-29s ║%n", com.astrology.core.AstrologyUtils.toZodiac(chart.getLagnaLongitude()));
            System.out.printf("%s╠══════════════════════════════════════════╣%s%n", ConsoleRenderer.GOLD, ConsoleRenderer.RESET);
            System.out.println("║ PLANETARY POSITIONS                      ║");
            System.out.printf("%s╠══════╦══════════╦═══════╦══════════════╦════════╣%s%n", ConsoleRenderer.GOLD, ConsoleRenderer.RESET);
            System.out.println("║ Planet│ Sign     │ Deg   │ Nakshatra    │ House  ║");
            System.out.printf("%s╠══════╬══════════╬═══════╬══════════════╬════════╣%s%n", ConsoleRenderer.GOLD, ConsoleRenderer.RESET);

            for (com.astrology.core.Planet p : com.astrology.core.Planet.values()) {
                if (p == com.astrology.core.Planet.GULIKA || p == com.astrology.core.Planet.MANDI) {
                    continue; // Optional skip
                }
                com.astrology.planets.PlanetPosition pos = chart.getPositions().get(p);
                if (pos != null) {
                    String pName = p.name();
                    pName = pName.substring(0, 1) + pName.substring(1).toLowerCase();
                    String signName = pos.rashi().getEnglishName();
                    String degStr = com.astrology.core.AstrologyUtils.toDMS(pos.siderealLongitude() % 30);
                    // Just take degree and minutes for compact view, e.g. 14°23'
                    degStr = degStr.substring(0, degStr.lastIndexOf('\'') + 1);
                    String nak = pos.nakshatra().getName();
                    if (nak.length() > 12) nak = nak.substring(0, 12);
                    int house = pos.house();

                    System.out.printf("║ %-5s│ %-9s│%-7s│ %-13s│   %-5d║%n", pName, signName, degStr, nak, house);
                }
            }
            System.out.printf("%s╚══════╩══════════╩═══════╩══════════════╩════════╝%s%n", ConsoleRenderer.GOLD, ConsoleRenderer.RESET);

            if ("NORTH".equalsIgnoreCase(style)) {
                System.out.println("NORTH INDIAN CHART (Available in GUI/PDF)");
            }
        } catch (Exception e) {
            System.err.println("Error calculating chart: " + e.getMessage());
        }
    }
}
