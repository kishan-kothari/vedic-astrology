package com.astrology.cli;

import java.util.List;

public class ConsoleRenderer {
    // ANSI color constants
    public static final String RESET = "\033[0m";
    public static final String GOLD = "\033[33m";
    public static final String CYAN = "\033[36m";
    public static final String GREEN = "\033[32m";
    public static final String RED = "\033[31m";
    public static final String BOLD = "\033[1m";
    
    // Box-drawing characters
    public static final String DOUBLE_H = "═";
    public static final String DOUBLE_V = "║";
    public static final String DOUBLE_TL = "╔";
    public static final String DOUBLE_TR = "╗";
    public static final String DOUBLE_BL = "╚";
    public static final String DOUBLE_BR = "╝";
    
    public static void printHeader(String title) {
        System.out.printf("%s╔══════════════════════════════════════════╗%s%n", GOLD, RESET);
        System.out.printf("%s║    %-37s ║%s%n", GOLD, title, RESET);
        System.out.printf("%s╠══════════════════════════════════════════╣%s%n", GOLD, RESET);
    }
    
    public static void printTable(String[] headers, List<String[]> rows, int[] widths) {
        // Simple implementation for table printing
    }
    
    public static String planetSymbol(String p) {
        switch (p.toUpperCase()) {
            case "SUN": return "☉";
            case "MOON": return "☽";
            case "MARS": return "♂";
            case "MERCURY": return "☿";
            case "JUPITER": return "♃";
            case "VENUS": return "♀";
            case "SATURN": return "♄";
            case "RAHU": return "☊";
            case "KETU": return "☋";
            default: return p;
        }
    }
    
    public static String signSymbol(String r) {
        switch (r.toUpperCase()) {
            case "ARIES": return "♈";
            case "TAURUS": return "♉";
            case "GEMINI": return "♊";
            case "CANCER": return "♋";
            case "LEO": return "♌";
            case "VIRGO": return "♍";
            case "LIBRA": return "♎";
            case "SCORPIO": return "♏";
            case "SAGITTARIUS": return "♐";
            case "CAPRICORN": return "♑";
            case "AQUARIUS": return "♒";
            case "PISCES": return "♓";
            default: return r;
        }
    }
    
    public static String highlight(String text, boolean current) {
        return current ? GREEN + BOLD + text + RESET : text;
    }
}
