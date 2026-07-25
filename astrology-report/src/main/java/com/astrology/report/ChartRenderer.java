package com.astrology.report;

import com.astrology.planets.BirthChart;
import com.astrology.core.Planet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Renders astrological charts in ASCII or SVG formats.
 */
public class ChartRenderer {

    /**
     * Renders a North Indian style chart (diamond) as ASCII.
     */
    public String renderNorthIndianAscii(BirthChart chart) {
        StringBuilder sb = new StringBuilder();
        sb.append("+-----------------------+\n");
        sb.append("|       |       |       |\n");
        sb.append("|   ").append(padRight(getHouseContent(chart, 12), 4))
          .append("|   ").append(padRight(getHouseContent(chart, 1), 4))
          .append("|   ").append(padRight(getHouseContent(chart, 2), 4)).append("|\n");
        sb.append("|       |       |       |\n");
        sb.append("+-------+-------+-------+\n");
        sb.append("|       |       |       |\n");
        sb.append("|   ").append(padRight(getHouseContent(chart, 11), 4))
          .append("|       |   ").append(padRight(getHouseContent(chart, 3), 4)).append("|\n");
        sb.append("|       |       |       |\n");
        sb.append("+-------+-------+-------+\n");
        sb.append("|       |       |       |\n");
        sb.append("|   ").append(padRight(getHouseContent(chart, 10), 4))
          .append("|   ").append(padRight(getHouseContent(chart, 9), 4))
          .append("|   ").append(padRight(getHouseContent(chart, 4), 4)).append("|\n");
        sb.append("|       |       |       |\n");
        sb.append("+-------+-------+-------+\n");
        // This is a simplified square representation, as drawing a true ASCII diamond is complex.
        return sb.toString();
    }

    /**
     * Renders a South Indian style chart as ASCII.
     */
    public String renderSouthIndianAscii(BirthChart chart) {
        StringBuilder sb = new StringBuilder();
        sb.append("+--------+--------+--------+--------+\n");
        sb.append("| Pi     | Ar     | Ta     | Ge     |\n");
        sb.append("| ").append(padRight(getSignContent(chart, 12), 7)).append("| ")
          .append(padRight(getSignContent(chart, 1), 7)).append("| ")
          .append(padRight(getSignContent(chart, 2), 7)).append("| ")
          .append(padRight(getSignContent(chart, 3), 7)).append("|\n");
        sb.append("+--------+--------+--------+--------+\n");
        sb.append("| Aq     |                 | Ca     |\n");
        sb.append("| ").append(padRight(getSignContent(chart, 11), 7)).append("|                 | ")
          .append(padRight(getSignContent(chart, 4), 7)).append("|\n");
        sb.append("+--------+                 +--------+\n");
        sb.append("| Cp     |                 | Le     |\n");
        sb.append("| ").append(padRight(getSignContent(chart, 10), 7)).append("|                 | ")
          .append(padRight(getSignContent(chart, 5), 7)).append("|\n");
        sb.append("+--------+--------+--------+--------+\n");
        sb.append("| Sg     | Sc     | Li     | Vi     |\n");
        sb.append("| ").append(padRight(getSignContent(chart, 9), 7)).append("| ")
          .append(padRight(getSignContent(chart, 8), 7)).append("| ")
          .append(padRight(getSignContent(chart, 7), 7)).append("| ")
          .append(padRight(getSignContent(chart, 6), 7)).append("|\n");
        sb.append("+--------+--------+--------+--------+\n");
        return sb.toString();
    }

    public String renderNorthIndianSvg(BirthChart chart, int width, int height) {
        return "<svg width=\"" + width + "\" height=\"" + height + "\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
               "  <rect width=\"100%\" height=\"100%\" fill=\"none\" stroke=\"black\" />\n" +
               "  <line x1=\"0\" y1=\"0\" x2=\"" + width + "\" y2=\"" + height + "\" stroke=\"black\" />\n" +
               "  <line x1=\"0\" y1=\"" + height + "\" x2=\"" + width + "\" y2=\"0\" stroke=\"black\" />\n" +
               "  <line x1=\"" + (width/2) + "\" y1=\"0\" x2=\"" + width + "\" y2=\"" + (height/2) + "\" stroke=\"black\" />\n" +
               "  <line x1=\"" + width + "\" y1=\"" + (height/2) + "\" x2=\"" + (width/2) + "\" y2=\"" + height + "\" stroke=\"black\" />\n" +
               "  <line x1=\"" + (width/2) + "\" y1=\"" + height + "\" x2=\"0\" y2=\"" + (height/2) + "\" stroke=\"black\" />\n" +
               "  <line x1=\"0\" y1=\"" + (height/2) + "\" x2=\"" + (width/2) + "\" y2=\"0\" stroke=\"black\" />\n" +
               "</svg>";
    }

    public String renderSouthIndianSvg(BirthChart chart, int width, int height) {
        return "<svg width=\"" + width + "\" height=\"" + height + "\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
               "  <rect width=\"100%\" height=\"100%\" fill=\"none\" stroke=\"black\" />\n" +
               "  <line x1=\"" + (width/4) + "\" y1=\"0\" x2=\"" + (width/4) + "\" y2=\"" + height + "\" stroke=\"black\" />\n" +
               "  <line x1=\"" + (width/2) + "\" y1=\"0\" x2=\"" + (width/2) + "\" y2=\"" + (height/4) + "\" stroke=\"black\" />\n" +
               "  <line x1=\"" + (width/2) + "\" y1=\"" + (3*height/4) + "\" x2=\"" + (width/2) + "\" y2=\"" + height + "\" stroke=\"black\" />\n" +
               "  <line x1=\"" + (3*width/4) + "\" y1=\"0\" x2=\"" + (3*width/4) + "\" y2=\"" + height + "\" stroke=\"black\" />\n" +
               "  <line x1=\"0\" y1=\"" + (height/4) + "\" x2=\"" + width + "\" y2=\"" + (height/4) + "\" stroke=\"black\" />\n" +
               "  <line x1=\"0\" y1=\"" + (height/2) + "\" x2=\"" + (width/4) + "\" y2=\"" + (height/2) + "\" stroke=\"black\" />\n" +
               "  <line x1=\"" + (3*width/4) + "\" y1=\"" + (height/2) + "\" x2=\"" + width + "\" y2=\"" + (height/2) + "\" stroke=\"black\" />\n" +
               "  <line x1=\"0\" y1=\"" + (3*height/4) + "\" x2=\"" + width + "\" y2=\"" + (3*height/4) + "\" stroke=\"black\" />\n" +
               "</svg>";
    }

    private String getHouseContent(BirthChart chart, int house) {
        if (chart == null) return "";
        return chart.getPlanetsInHouse(house).stream()
                .map(p -> p.name().substring(0, Math.min(2, p.name().length())))
                .collect(Collectors.joining(" "));
    }

    private String getSignContent(BirthChart chart, int sign) {
        if (chart == null) return "";
        // Find planets whose rashi number matches this sign index (1-based)
        return chart.getPositions().values().stream()
                .filter(pp -> pp.rashi() != null && pp.rashi().getNumber() == sign)
                .map(pp -> pp.planet().name().substring(0, Math.min(2, pp.planet().name().length())))
                .collect(Collectors.joining(" "));
    }

    private String padRight(String s, int n) {
        return String.format("%-" + n + "s", s).substring(0, n);
    }
}
