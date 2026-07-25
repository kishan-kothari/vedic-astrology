package com.astrology.report;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.font.PDFont;

import com.astrology.planets.BirthChart;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Generates a complete multi-page PDF using Apache PDFBox 3.
 */
public class PdfReportGenerator {
    private static final float PAGE_WIDTH = 595f; // A4
    private static final float PAGE_HEIGHT = 842f;
    private static final float MARGIN = 40f;

    // Color scheme (premium dark theme elements)
    private static final float[] HEADER_COLOR = {0.1f, 0.2f, 0.5f}; // dark blue
    private static final float[] ACCENT_COLOR = {0.8f, 0.6f, 0.1f}; // gold
    private static final float[] TABLE_HEADER = {0.2f, 0.3f, 0.6f};
    private static final float[] ALT_ROW = {0.95f, 0.95f, 1.0f};

    // Cached fonts to avoid PDF bloat
    private PDFont fontHelvetica;
    private PDFont fontHelveticaBold;

    public void generateReport(ReportData data, Path outputPath) throws IOException {
        fontHelvetica = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
        fontHelveticaBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
        try (PDDocument doc = new PDDocument()) {
            // Page 1: Birth Information + Planetary Positions Table
            PDPage page1 = addPage(doc);
            try (PDPageContentStream cs = new PDPageContentStream(doc, page1)) {
                addHeader(cs, "Vedic Astrology (Jyotish) Birth Chart Analysis", data);
                addPlanetaryTable(cs, data, PAGE_HEIGHT - 150f);
                addFooter(cs, 1, page1);
            }

            // Page 2: Avakhada Chakra & Basic Details
            PDPage pageAvakhada = addPage(doc);
            try (PDPageContentStream cs = new PDPageContentStream(doc, pageAvakhada)) {
                addHeader(cs, "Avakhada Chakra & Basic Details", data);
                addAvakhadaChakra(cs, data, PAGE_HEIGHT - 150f);
            }
            renderDivisionalChartPages(doc, data);

            // Vimshottari Dasha Tables (Handles its own pagination)
            renderDashaPages(doc, data);

            // Page 4: Detected Yogas
            PDPage page4 = addPage(doc);
            try (PDPageContentStream cs = new PDPageContentStream(doc, page4)) {
                addHeader(cs, "Astrological Yogas", data);
                addYogaTable(cs, data, PAGE_HEIGHT - 150f);
                addFooter(cs, 4, page4);
            }

            // Page 5: Shadbala Strength Table
            PDPage page5 = addPage(doc);
            try (PDPageContentStream cs = new PDPageContentStream(doc, page5)) {
                addHeader(cs, "Shadbala (Planetary Strengths)", data);
                addShadbalaTable(cs, data, PAGE_HEIGHT - 150f);
                addFooter(cs, 5, page5);
            }

            // Page 6: Ashtakavarga Bindus
            PDPage page6 = addPage(doc);
            try (PDPageContentStream cs = new PDPageContentStream(doc, page6)) {
                addHeader(cs, "Ashtakavarga", data);
                addAshtakavargaTable(cs, data, PAGE_HEIGHT - 150f);
                addFooter(cs, 6, page6);
            }
            // Detailed General Predictions
            renderDetailedPredictionsPages(doc, data);
            
            // Nakshatra Phal
            renderNakshatraPhalPages(doc, data);
            
            // Lal Kitab Predictions
            renderLalKitabPages(doc, data);
            
            // Vimshottari Mahadasha Detailed Phal
            renderMahadashaPhalPages(doc, data);

            // Dosha & Sade Sati Detailed
            renderDoshaAndSadeSatiPages(doc, data);

            // Page 9+: Divisional Charts & Timing (Paginated)
            renderDivisionalTimingsPages(doc, data);
            
            // Page 10+: Advanced Tables (KP, PAV, Yogini, Char)
            renderAdvancedTablesPages(doc, data);

            doc.save(outputPath.toFile());
        }
    }

    private PDPage addPage(PDDocument doc) {
        PDPage page = new PDPage(new org.apache.pdfbox.pdmodel.common.PDRectangle(PAGE_WIDTH, PAGE_HEIGHT));
        doc.addPage(page);
        return page;
    }

    private void addHeader(PDPageContentStream cs, String title, ReportData data) throws IOException {
        PDFont font = fontHelveticaBold;
        cs.beginText();
        cs.setFont(font, 18);
        cs.setNonStrokingColor(HEADER_COLOR[0], HEADER_COLOR[1], HEADER_COLOR[2]);
        cs.newLineAtOffset(MARGIN, PAGE_HEIGHT - 50f);
        cs.showText(title);
        cs.endText();

        // Basic birth info
        cs.beginText();
        cs.setFont(fontHelvetica, 10);
        cs.setNonStrokingColor(0f, 0f, 0f);
        cs.newLineAtOffset(MARGIN, PAGE_HEIGHT - 70f);
        String headerText = "Name: Sample User | Date: 01-Jan-2000 | Time: 12:00 PM";
        if (data.getBirthChart() != null && data.getBirthChart().getBirthData() != null) {
            com.astrology.planets.BirthData bd = data.getBirthChart().getBirthData();
            String name = bd.name();
            if (name == null || name.isBlank()) name = "Unknown";
            headerText = String.format("Name: %s | Date: %s | Time: %s", 
                name, 
                bd.localDateTime().toLocalDate().toString(), 
                bd.localDateTime().toLocalTime().toString());
        }
        cs.showText(headerText);
        cs.endText();
    }

    private void addFooter(PDPageContentStream cs, int pageNum, PDPage page) throws IOException {
        cs.beginText();
        cs.setFont(fontHelvetica, 10);
        cs.setNonStrokingColor(0.5f, 0.5f, 0.5f);
        cs.newLineAtOffset(PAGE_WIDTH / 2 - 10f, 20f);
        cs.showText("Page " + pageNum);
        cs.endText();
    }

    private void addPlanetaryTable(PDPageContentStream cs, ReportData data, float y) throws IOException {
        String[] headers = {"Planet", "Sign", "Deg", "Nak", "Pada", "House", "Speed", "R?", "Dignity"};
        float[] widths = {60f, 60f, 50f, 70f, 40f, 40f, 50f, 30f, 80f};
        drawTableHeader(cs, headers, widths, MARGIN, y, 20f);
        
        float curY = y - 20f;
        boolean alt = false;
        if (data.getBirthChart() != null) {
            for (com.astrology.core.Planet p : com.astrology.core.Planet.values()) {
                if (curY < MARGIN) break;
                com.astrology.planets.PlanetPosition pos = data.getBirthChart().getPlanet(p);
                if (pos == null) continue;
                String speed = String.format("%.2f°", pos.speedLongitude());
                String degStr = com.astrology.core.AstrologyUtils.toDMS(pos.siderealLongitude() % 30);
                int idx = degStr.lastIndexOf('\'');
                if(idx > 0) degStr = degStr.substring(0, idx + 1);
                
                String[] row = {
                    p.getName(),
                    pos.rashi().getEnglishName(),
                    degStr,
                    pos.nakshatra().getName(),
                    String.valueOf(pos.pada()),
                    String.valueOf(pos.house()),
                    speed,
                    pos.retrograde() ? "Y" : "N",
                    pos.dignitaryStatus().name()
                };
                drawTableRow(cs, row, widths, MARGIN, curY, 20f, alt);
                curY -= 20f;
                alt = !alt;
            }
        }
    }

    private String getShortName(com.astrology.core.Planet p) {
        String n = p.name();
        if (n.length() >= 2) return n.substring(0, 1) + n.substring(1, 2).toLowerCase();
        return n;
    }

    private void renderDivisionalChartPages(PDDocument doc, ReportData data) throws IOException {
        if (data.getDivisionalCharts() == null) return;
        
        int[] divisors = {1, 2, 3, 4, 7, 9, 10, 12, 16, 20, 24, 27, 30, 40, 45, 60};
        
        PDPage page = null;
        PDPageContentStream cs = null;
        int countOnPage = 0;
        
        for (int div : divisors) {
            com.astrology.divisional.DivisionalChart dChart = data.getDivisionalCharts().getChart(div);
            if (dChart == null && div != 1) continue; 
            
            if (countOnPage == 0) {
                page = addPage(doc);
                cs = new PDPageContentStream(doc, page);
                addHeader(cs, "Astrological Charts (Shodashavarga)", data);
            }
            
            float x = (countOnPage % 2 == 0) ? MARGIN : MARGIN + 280f;
            
            cs.beginText();
            cs.setFont(fontHelveticaBold, 14);
            cs.setNonStrokingColor(0f, 0f, 0f);
            cs.newLineAtOffset(x, PAGE_HEIGHT - 130f);
            cs.showText("D" + div + " Chart");
            cs.endText();
            
            if (div == 1 && data.getBirthChart() != null) {
                drawBirthChart(cs, data.getBirthChart(), x, PAGE_HEIGHT - 400f, 240f);
            } else if (dChart != null) {
                drawDivChart(cs, dChart, x, PAGE_HEIGHT - 400f, 240f);
            }
            
            countOnPage++;
            if (countOnPage == 2) {
                cs.close();
                countOnPage = 0;
            }
        }
        if (countOnPage > 0 && cs != null) {
            cs.close();
        }
    }

    private void drawNorthIndianChartBase(PDPageContentStream cs, java.util.Map<Integer, java.util.List<String>> houseContents, java.util.Map<Integer, Integer> houseSigns, float x, float y, float size) throws IOException {
        cs.setStrokingColor(0f, 0f, 0f);
        cs.setLineWidth(1f);
        cs.addRect(x, y, size, size);
        cs.moveTo(x, y); cs.lineTo(x + size, y + size);
        cs.moveTo(x, y + size); cs.lineTo(x + size, y);
        cs.moveTo(x + size/2, y); cs.lineTo(x + size, y + size/2);
        cs.moveTo(x + size, y + size/2); cs.lineTo(x + size/2, y + size);
        cs.moveTo(x + size/2, y + size); cs.lineTo(x, y + size/2);
        cs.moveTo(x, y + size/2); cs.lineTo(x + size/2, y);
        cs.stroke();

        float[][] houseCenters = {
            {0.50f, 0.70f}, {0.25f, 0.85f}, {0.15f, 0.75f}, {0.30f, 0.50f},
            {0.15f, 0.25f}, {0.25f, 0.15f}, {0.50f, 0.30f}, {0.75f, 0.15f},
            {0.85f, 0.25f}, {0.70f, 0.50f}, {0.85f, 0.75f}, {0.75f, 0.85f}
        };

        PDFont font = fontHelvetica;
        for (int h = 1; h <= 12; h++) {
            float cx = x + size * houseCenters[h - 1][0];
            float cy = y + size * houseCenters[h - 1][1];

            Integer sign = houseSigns.get(h);
            if (sign != null) {
                cs.beginText();
                cs.setFont(font, 8);
                cs.setNonStrokingColor(0.6f, 0.1f, 0.1f);
                cs.newLineAtOffset(cx - 3f, cy + 12f); 
                cs.showText(String.valueOf(sign));
                cs.endText();
            }

            java.util.List<String> planets = houseContents.get(h);
            if (planets != null && !planets.isEmpty()) {
                cs.beginText();
                cs.setFont(font, 10);
                cs.setNonStrokingColor(0f, 0f, 0f);
                String text = String.join(" ", planets);
                float textWidth = font.getStringWidth(text) / 1000f * 10f;
                cs.newLineAtOffset(cx - textWidth/2, cy - 5f);
                cs.showText(text);
                cs.endText();
            }
        }
    }

    private void drawBirthChart(PDPageContentStream cs, BirthChart chart, float x, float y, float size) throws IOException {
        java.util.Map<Integer, java.util.List<String>> contents = new java.util.HashMap<>();
        java.util.Map<Integer, Integer> signs = new java.util.HashMap<>();
        int lagnaSign = chart.getLagnaRashi().getNumber();
        for (int h = 1; h <= 12; h++) {
            signs.put(h, (lagnaSign + h - 2) % 12 + 1);
            contents.put(h, new java.util.ArrayList<>());
        }
        contents.get(1).add("As");
        for (java.util.Map.Entry<com.astrology.core.Planet, com.astrology.planets.PlanetPosition> entry : chart.getPositions().entrySet()) {
            if (entry.getKey().isShadow() && entry.getKey().getSeCode() < 0) continue;
            contents.get(entry.getValue().house()).add(getShortName(entry.getKey()));
        }
        drawNorthIndianChartBase(cs, contents, signs, x, y, size);
    }

    private void drawDivChart(PDPageContentStream cs, com.astrology.divisional.DivisionalChart chart, float x, float y, float size) throws IOException {
        java.util.Map<Integer, java.util.List<String>> contents = new java.util.HashMap<>();
        java.util.Map<Integer, Integer> signs = new java.util.HashMap<>();
        int lagnaSign = chart.lagnaRashi().getNumber();
        for (int h = 1; h <= 12; h++) {
            signs.put(h, (lagnaSign + h - 2) % 12 + 1);
            contents.put(h, new java.util.ArrayList<>());
        }
        contents.get(1).add("As");
        for (java.util.Map.Entry<com.astrology.core.Planet, Integer> entry : chart.getHousePositions().entrySet()) {
            if (entry.getKey().isShadow() && entry.getKey().getSeCode() < 0) continue;
            contents.get(entry.getValue()).add(getShortName(entry.getKey()));
        }
        drawNorthIndianChartBase(cs, contents, signs, x, y, size);
    }

    private void renderDashaPages(PDDocument doc, ReportData data) throws IOException {
        if (data.getVimshottariDashas() == null) return;
        
        PDPage page = addPage(doc);
        PDPageContentStream cs = new PDPageContentStream(doc, page);
        addHeader(cs, "Vimshottari Dasha Analysis", data);
        
        String[] headers = {"Period", "Lord", "Start Date", "End Date"};
        float[] widths = {150f, 100f, 100f, 100f};
        float curY = PAGE_HEIGHT - 150f;
        drawTableHeader(cs, headers, widths, MARGIN, curY, 20f);
        curY -= 20f;
        
        boolean alt = false;
        java.time.LocalDate today = java.time.LocalDate.now();
        
        for (com.astrology.dasha.DashaPeriod md : data.getVimshottariDashas()) {
            curY = drawDashaRow(doc, cs, page, headers, widths, curY, alt, "MD", md.lord().getName(), md, 0);
            alt = !alt;
            
            boolean mdActive = md.isActive(today);
            
            if (md.subPeriods() != null) {
                for (com.astrology.dasha.DashaPeriod ad : md.subPeriods()) {
                    curY = drawDashaRow(doc, cs, page, headers, widths, curY, alt, "  AD", ad.lord().getName(), ad, 10);
                    alt = !alt;
                    
                    if (mdActive && ad.subPeriods() != null) {
                        for (com.astrology.dasha.DashaPeriod pd : ad.subPeriods()) {
                            curY = drawDashaRow(doc, cs, page, headers, widths, curY, alt, "    PD", pd.lord().getName(), pd, 20);
                            alt = !alt;
                            
                            if (pd.subPeriods() != null) {
                                for (com.astrology.dasha.DashaPeriod sd : pd.subPeriods()) {
                                    curY = drawDashaRow(doc, cs, page, headers, widths, curY, alt, "      SD", sd.lord().getName(), sd, 30);
                                    alt = !alt;
                                }
                            }
                        }
                    }
                }
            }
            if (curY < MARGIN + 40f) {
                cs.close();
                page = addPage(doc);
                cs = new PDPageContentStream(doc, page);
                curY = PAGE_HEIGHT - 100f;
                drawTableHeader(cs, headers, widths, MARGIN, curY, 20f);
                curY -= 20f;
            }
        }
        cs.close();
    }
    
    private float drawDashaRow(PDDocument doc, PDPageContentStream cs, PDPage page, String[] headers, float[] widths, float curY, boolean alt, String level, String lord, com.astrology.dasha.DashaPeriod d, float indent) throws IOException {
        String[] row = { level, lord, d.startDate().toString(), d.endDate().toString() };
        float currX = MARGIN;
        float[] bgColor = alt ? ALT_ROW : new float[]{1f, 1f, 1f};
        for (int i = 0; i < row.length; i++) {
            drawRect(cs, currX, curY, widths[i], 20f, bgColor, true);
            float xOffset = (i == 0) ? indent + 5 : 5;
            drawText(cs, row[i], currX + xOffset, curY + 5, fontHelvetica, 10, new float[]{0f, 0f, 0f});
            currX += widths[i];
        }
        return curY - 20f;
    }

    private void addYogaTable(PDPageContentStream cs, ReportData data, float y) throws IOException {
        String[] headers = {"Yoga Name", "Category", "Effects"};
        float[] widths = {150f, 100f, 250f};
        drawTableHeader(cs, headers, widths, MARGIN, y, 20f);
        
        float curY = y - 20f;
        boolean alt = false;
        if (data.getYogas() != null) {
            java.util.Map<String, com.astrology.yoga.Yoga> groupedYogas = new java.util.LinkedHashMap<>();
            for (com.astrology.yoga.Yoga yoga : data.getYogas()) {
                String baseName = yoga.name().replaceAll("\\d+$", ""); // Group RajaYoga1 and RajaYoga2 to RajaYoga
                groupedYogas.putIfAbsent(baseName, yoga); // Keep the first occurrence
            }
            
            for (com.astrology.yoga.Yoga yoga : groupedYogas.values()) {
                if (curY < MARGIN) break;
                String baseName = yoga.name().replaceAll("\\d+$", "");
                String effects = yoga.effects();
                if (effects == null) effects = "";
                if (effects.length() > 40) effects = effects.substring(0, 37) + "...";
                String[] row = {
                    baseName,
                    yoga.category().name(),
                    effects
                };
                drawTableRow(cs, row, widths, MARGIN, curY, 20f, alt);
                curY -= 20f;
                alt = !alt;
            }
        }
    }

    private void addShadbalaTable(PDPageContentStream cs, ReportData data, float y) throws IOException {
        String[] headers = {"Planet", "Total Strength", "Required", "Status"};
        float[] widths = {100f, 100f, 100f, 100f};
        drawTableHeader(cs, headers, widths, MARGIN, y, 20f);
        
        float curY = y - 20f;
        boolean alt = false;
        if (data.getShadbala() != null) {
            for (com.astrology.core.Planet p : com.astrology.core.Planet.values()) {
                if (curY < MARGIN) break;
                com.astrology.strength.ShadbalaResult sr = data.getShadbala().get(p);
                if (sr == null) continue;
                String[] row = {
                    p.getName(),
                    String.format("%.2f", sr.totalRupas()),
                    String.format("%.2f", sr.requiredRupas()),
                    sr.isSufficient() ? "Strong" : "Weak"
                };
                drawTableRow(cs, row, widths, MARGIN, curY, 20f, alt);
                curY -= 20f;
                alt = !alt;
            }
        }
    }

    private void addAshtakavargaTable(PDPageContentStream cs, ReportData data, float y) throws IOException {
        String[] headers = {"Planet", "Ar", "Ta", "Ge", "Ca", "Le", "Vi", "Li", "Sc", "Sg", "Cp", "Aq", "Pi"};
        float[] widths = {60f, 30f, 30f, 30f, 30f, 30f, 30f, 30f, 30f, 30f, 30f, 30f, 30f};
        drawTableHeader(cs, headers, widths, MARGIN, y, 20f);
        
        float curY = y - 20f;
        boolean alt = false;
        if (data.getAshtakavarga() != null) {
            for (com.astrology.core.Planet p : com.astrology.core.Planet.values()) {
                if (p.isShadow() || p.getSeCode() < 0) continue; 
                try {
                    int[] bindus = new int[12];
                    for(int i=0; i<12; i++) bindus[i] = data.getAshtakavarga().getBhinnaBindus(p, i);
                    String[] row = new String[13];
                    row[0] = p.getName();
                    for(int i=0; i<12; i++) row[i+1] = String.valueOf(bindus[i]);
                    drawTableRow(cs, row, widths, MARGIN, curY, 20f, alt);
                    curY -= 20f;
                    alt = !alt;
                } catch (Exception e) {}
            }
            if (curY >= MARGIN) {
                String[] row = new String[13];
                row[0] = "Total";
                for(int i=0; i<12; i++) {
                    row[i+1] = String.valueOf(data.getAshtakavarga().getSarvashtakavargaBindus(i));
                }
                drawTableRow(cs, row, widths, MARGIN, curY, 20f, alt);
            }
        }
    }

    private void drawTableHeader(PDPageContentStream cs, String[] headers, float[] colWidths, float x, float y, float rowHeight) throws IOException {
        float currX = x;
        for (int i = 0; i < headers.length; i++) {
            drawRect(cs, currX, y, colWidths[i], rowHeight, TABLE_HEADER, true);
            drawText(cs, headers[i], currX + 5, y + 5, fontHelveticaBold, 10, new float[]{1f, 1f, 1f});
            currX += colWidths[i];
        }
    }

    private void drawTableRow(PDPageContentStream cs, String[] cells, float[] colWidths, float x, float y, float rowHeight, boolean alternate) throws IOException {
        float currX = x;
        float[] bgColor = alternate ? ALT_ROW : new float[]{1f, 1f, 1f};
        for (int i = 0; i < cells.length; i++) {
            drawRect(cs, currX, y, colWidths[i], rowHeight, bgColor, true);
            drawText(cs, cells[i], currX + 5, y + 5, fontHelvetica, 10, new float[]{0f, 0f, 0f});
            currX += colWidths[i];
        }
    }

    private void drawRect(PDPageContentStream cs, float x, float y, float w, float h, float[] color, boolean filled) throws IOException {
        if (filled) {
            cs.setNonStrokingColor(color[0], color[1], color[2]);
            cs.addRect(x, y, w, h);
            cs.fill();
        }
        cs.setStrokingColor(0.8f, 0.8f, 0.8f);
        cs.addRect(x, y, w, h);
        cs.stroke();
    }

    private void drawText(PDPageContentStream cs, String text, float x, float y, PDFont font, float size, float[] color) throws IOException {
        if (text == null) text = "";
        cs.beginText();
        cs.setFont(font, size);
        cs.setNonStrokingColor(color[0], color[1], color[2]);
        cs.newLineAtOffset(x, y);
        cs.showText(text);
        cs.endText();
    }

    private float drawTextWrapped(PDPageContentStream cs, String text, float x, float y, float width, PDFont font, float size, float[] color) throws IOException {
        if (text == null || text.isEmpty()) return y;
        
        float curY = y;
        for (String paragraph : text.split("\n")) {
            if (paragraph.trim().isEmpty()) {
                curY -= size * 1.5f;
                continue;
            }
            java.util.List<String> lines = new java.util.ArrayList<>();
            int lastSpace = -1;
            String currentText = paragraph;
            while (currentText.length() > 0) {
                int spaceIndex = currentText.indexOf(' ', lastSpace + 1);
                if (spaceIndex < 0) spaceIndex = currentText.length();
                String subString = currentText.substring(0, spaceIndex);
                
                // Remove any unprintable characters that might crash getStringWidth
                subString = subString.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");
                
                float currentWidth = 0;
                try {
                    currentWidth = font.getStringWidth(subString) / 1000 * size;
                } catch (IllegalArgumentException e) {
                    // Fallback width if font still complains
                    currentWidth = subString.length() * (size * 0.5f);
                }
                
                if (currentWidth > width) {
                    if (lastSpace < 0) lastSpace = spaceIndex;
                    subString = currentText.substring(0, lastSpace);
                    lines.add(subString);
                    currentText = currentText.substring(lastSpace).trim();
                    lastSpace = -1;
                } else if (spaceIndex == currentText.length()) {
                    lines.add(currentText);
                    currentText = "";
                } else {
                    lastSpace = spaceIndex;
                }
            }
            
            for (String line : lines) {
                // Final sanitize before drawing
                line = line.replaceAll("[\\p{Cntrl}]", "");
                drawText(cs, line, x, curY, font, size, color);
                curY -= size * 1.5f;
            }
        }
        return curY;
    }

    private void addAvakhadaChakra(PDPageContentStream cs, ReportData data, float y) throws IOException {
        if (data.getBirthChart() == null) return;
        com.astrology.planets.PlanetPosition moonPos = data.getBirthChart().getPlanet(com.astrology.core.Planet.MOON);
        if (moonPos == null) return;
        
        com.astrology.core.Nakshatra nak = moonPos.nakshatra();
        com.astrology.core.Rashi rashi = moonPos.rashi();
        com.astrology.planets.BirthChart.Paya paya = data.getBirthChart().getPaya();
        
        String[] headers = {"Detail", "Value"};
        float[] widths = {150f, 250f};
        drawTableHeader(cs, headers, widths, MARGIN, y, 20f);
        
        float curY = y - 20f;
        String[][] rows = {
            {"Varna", rashi.getVarna() != null ? rashi.getVarna().name() : "N/A"},
            {"Vashya", rashi.getVashya(moonPos.siderealLongitude() % 30.0) != null ? rashi.getVashya(moonPos.siderealLongitude() % 30.0).name() : "N/A"},
            {"Yoni", nak.getYoni() != null ? nak.getYoni().name() : "N/A"},
            {"Gana", nak.getGana() != null ? nak.getGana().name() : "N/A"},
            {"Nadi", nak.getNadi() != null ? nak.getNadi().name() : "N/A"},
            {"Paya", paya != null ? paya.name() : "N/A"},
            {"Sign Lord", rashi.getLord().getName()},
            {"Nakshatra Lord", nak.getLord().getName()}
        };
        
        boolean alt = false;
        for (String[] row : rows) {
            drawTableRow(cs, row, widths, MARGIN, curY, 20f, alt);
            curY -= 20f;
            alt = !alt;
        }
    }

    private void renderDetailedPredictionsPages(PDDocument doc, ReportData data) throws IOException {
        if (data.getBirthChart() == null) return;
        com.astrology.core.Rashi lagna = data.getBirthChart().getLagnaRashi();
        PDPage page = addPage(doc);
        PDPageContentStream cs = new PDPageContentStream(doc, page);
        addHeader(cs, "Detailed Life Predictions", data);
        PDFont font = fontHelvetica;
        PDFont fontBold = fontHelveticaBold;
        float[] color = new float[]{0f, 0f, 0f};
        float curY = PAGE_HEIGHT - 150f;

        String[] sections = {"Character & Personality", "Health & Wellness", "Career & Profession", "Wealth & Finance"};
        String[] texts = {
            GeneralPrediction.getCharacterPrediction(lagna),
            GeneralPrediction.getHealthPrediction(lagna),
            GeneralPrediction.getCareerPrediction(lagna),
            GeneralPrediction.getFinancePrediction(lagna)
        };

        for (int i = 0; i < sections.length; i++) {
            if (curY < 150f) {
                cs.close();
                page = addPage(doc);
                cs = new PDPageContentStream(doc, page);
                addHeader(cs, "Detailed Life Predictions (Contd.)", data);
                curY = PAGE_HEIGHT - 100f;
            }
            drawText(cs, sections[i] + ":", MARGIN, curY, fontBold, 12, color);
            curY -= 20f;
            curY = drawTextWrapped(cs, texts[i], MARGIN, curY, PAGE_WIDTH - 2 * MARGIN, font, 10, color);
            curY -= 20f;
        }
        cs.close();
    }

    private void renderNakshatraPhalPages(PDDocument doc, ReportData data) throws IOException {
        if (data.getBirthChart() == null || data.getBirthChart().getPlanet(com.astrology.core.Planet.MOON) == null) return;
        com.astrology.core.Nakshatra nak = data.getBirthChart().getPlanet(com.astrology.core.Planet.MOON).nakshatra();
        PDPage page = addPage(doc);
        PDPageContentStream cs = new PDPageContentStream(doc, page);
        addHeader(cs, "Nakshatra Phal", data);
        PDFont font = fontHelvetica;
        PDFont fontBold = fontHelveticaBold;
        float[] color = new float[]{0f, 0f, 0f};
        float curY = PAGE_HEIGHT - 150f;

        drawText(cs, "Your Birth Nakshatra is " + nak.getName() + ":", MARGIN, curY, fontBold, 12, color);
        curY -= 20f;
        String nakText = NakshatraPrediction.getNakshatraPrediction(nak);
        curY = drawTextWrapped(cs, nakText, MARGIN, curY, PAGE_WIDTH - 2 * MARGIN, font, 10, color);
        cs.close();
    }

    private void renderLalKitabPages(PDDocument doc, ReportData data) throws IOException {
        if (data.getBirthChart() == null) return;
        PDPage page = addPage(doc);
        PDPageContentStream cs = new PDPageContentStream(doc, page);
        addHeader(cs, "Lal Kitab Predictions & Remedies", data);
        PDFont font = fontHelvetica;
        PDFont fontBold = fontHelveticaBold;
        float[] color = new float[]{0f, 0f, 0f};
        float curY = PAGE_HEIGHT - 150f;

        for (com.astrology.core.Planet p : com.astrology.core.Planet.values()) {
            if (p == com.astrology.core.Planet.GULIKA || p == com.astrology.core.Planet.MANDI) continue;
            int house = data.getBirthChart().getHouseOfPlanet(p);
            if (house == 0) continue;

            if (curY < 150f) {
                cs.close();
                page = addPage(doc);
                cs = new PDPageContentStream(doc, page);
                addHeader(cs, "Lal Kitab Predictions & Remedies (Contd.)", data);
                curY = PAGE_HEIGHT - 100f;
            }
            drawText(cs, p.getName() + " in House " + house + ":", MARGIN, curY, fontBold, 12, color);
            curY -= 20f;
            String text = LalKitabPrediction.getLalKitabPrediction(p, house);
            curY = drawTextWrapped(cs, text, MARGIN, curY, PAGE_WIDTH - 2 * MARGIN, font, 10, color);
            curY -= 20f;
        }
        cs.close();
    }

    private void renderMahadashaPhalPages(PDDocument doc, ReportData data) throws IOException {
        if (data.getVimshottariDashas() == null || data.getBirthChart() == null) return;
        PDPage page = addPage(doc);
        PDPageContentStream cs = new PDPageContentStream(doc, page);
        addHeader(cs, "Vimshottari Mahadasha Phal", data);
        PDFont font = fontHelvetica;
        PDFont fontBold = fontHelveticaBold;
        float[] color = new float[]{0f, 0f, 0f};
        float curY = PAGE_HEIGHT - 150f;

        for (com.astrology.dasha.DashaPeriod md : data.getVimshottariDashas()) {
            if (curY < 150f) {
                cs.close();
                page = addPage(doc);
                cs = new PDPageContentStream(doc, page);
                addHeader(cs, "Vimshottari Mahadasha Phal (Contd.)", data);
                curY = PAGE_HEIGHT - 100f;
            }
            drawText(cs, md.lord().getName() + " Mahadasha (" + md.startDate().getYear() + " - " + md.endDate().getYear() + "):", MARGIN, curY, fontBold, 12, color);
            curY -= 20f;
            int house = data.getBirthChart().getHouseOfPlanet(md.lord());
            String text = MahadashaPrediction.getMahadashaPrediction(md.lord(), house);
            curY = drawTextWrapped(cs, text, MARGIN, curY, PAGE_WIDTH - 2 * MARGIN, font, 10, color);
            curY -= 20f;
        }
        cs.close();
    }

    private void renderDoshaAndSadeSatiPages(PDDocument doc, ReportData data) throws IOException {
        PDPage page = addPage(doc);
        PDPageContentStream cs = new PDPageContentStream(doc, page);
        addHeader(cs, "Dosha Analysis & Sade Sati Report", data);
        PDFont font = fontHelvetica;
        PDFont fontBold = fontHelveticaBold;
        float[] color = new float[]{0f, 0f, 0f};
        float curY = PAGE_HEIGHT - 150f;

        boolean isManglik = false;
        if (data.getYogas() != null) {
            for (com.astrology.yoga.Yoga yoga : data.getYogas()) {
                if (yoga.name().contains("Mangal")) isManglik = true;
            }
        }

        drawText(cs, "Mangal Dosha Analysis:", MARGIN, curY, fontBold, 12, color);
        curY -= 20f;
        String mangalText = PredictionEngine.getMangalDoshaExplanation(isManglik, "en");
        curY = drawTextWrapped(cs, mangalText, MARGIN, curY, PAGE_WIDTH - 2*MARGIN, font, 10, color);

        curY -= 40f;
        drawText(cs, "Sade Sati Report:", MARGIN, curY, fontBold, 12, color);
        curY -= 20f;
        if (data.getSadeSatiPhases() != null && !data.getSadeSatiPhases().isEmpty()) {
            String[] headers = {"Phase", "Saturn Sign", "Start Date", "End Date"};
            float[] widths = {80f, 100f, 100f, 100f};
            drawTableHeader(cs, headers, widths, MARGIN, curY, 20f);
            curY -= 20f;
            boolean alt = false;
            for (com.astrology.transit.SadeSatiCalculator.SadeSatiPhase phase : data.getSadeSatiPhases()) {
                String[] row = {
                    phase.phaseName(),
                    phase.saturnSign().getEnglishName(),
                    phase.startDate().toString(),
                    phase.endDate().toString()
                };
                drawTableRow(cs, row, widths, MARGIN, curY, 20f, alt);
                curY -= 20f;
                alt = !alt;
            }
            
            curY -= 20f;
            drawText(cs, "Understanding the 3 Phases of Sade Sati:", MARGIN, curY, fontBold, 11, color);
            curY -= 20f;
            curY = drawTextWrapped(cs, "**Rising Phase:** " + SadeSatiPrediction.getRisingPhaseText(), MARGIN, curY, PAGE_WIDTH - 2*MARGIN, font, 10, color);
            curY -= 10f;
            curY = drawTextWrapped(cs, "**Peak Phase:** " + SadeSatiPrediction.getPeakPhaseText(), MARGIN, curY, PAGE_WIDTH - 2*MARGIN, font, 10, color);
            curY -= 10f;
            curY = drawTextWrapped(cs, "**Setting Phase:** " + SadeSatiPrediction.getSettingPhaseText(), MARGIN, curY, PAGE_WIDTH - 2*MARGIN, font, 10, color);
        }
        cs.close();
    }

    private void renderDivisionalTimingsPages(PDDocument doc, ReportData data) throws IOException {
        if (data.getDivisionalCharts() == null || data.getVimshottariDashas() == null) return;
        String lang = "en"; // Force English for PDF
        PDFont font = fontHelvetica;
        PDFont fontBold = fontHelveticaBold;
        float[] color = new float[]{0f, 0f, 0f};
        
        PDPage page = addPage(doc);
        PDPageContentStream cs = new PDPageContentStream(doc, page);
        addHeader(cs, "Shodashavarga Timings", data);
        
        float curY = PAGE_HEIGHT - 150f;

        int[] specificCharts = {1, 2, 3, 4, 7, 9, 10, 12, 16, 20, 24, 27, 30, 40, 45, 60}; 
        for (int div : specificCharts) {
            if (curY < 150f) { 
                cs.close();
                page = addPage(doc);
                cs = new PDPageContentStream(doc, page);
                addHeader(cs, "Shodashavarga Timings (Contd.)", data);
                curY = PAGE_HEIGHT - 100f;
            }
            
            drawText(cs, "D" + div + " Chart Predictions:", MARGIN, curY, fontBold, 12, color);
            curY -= 20f;
            String exp = PredictionEngine.getDivisionalChartExplanation(div, lang);
            curY = drawTextWrapped(cs, exp, MARGIN, curY, PAGE_WIDTH - 2*MARGIN, font, 10, color);
            curY -= 10f;
            
            java.util.List<DivisionalTimingEngine.TimingEvent> timings = 
                DivisionalTimingEngine.calculatePositiveTimings(div, data.getDivisionalCharts(), data.getVimshottariDashas(), lang);
            
            int limit = Math.min(3, timings.size());
            for (int i=0; i<limit; i++) {
                DivisionalTimingEngine.TimingEvent e = timings.get(i);
                String tStr = e.periodName() + " (" + e.startDate() + " to " + e.endDate() + "): " + e.description();
                curY = drawTextWrapped(cs, tStr, MARGIN + 20f, curY, PAGE_WIDTH - 2*MARGIN - 20f, font, 9, color);
                curY -= 5f;
            }
            curY -= 20f;
        }
        cs.close();
    }
    
    private void renderAdvancedTablesPages(PDDocument doc, ReportData data) throws IOException {
        PDPage page = addPage(doc);
        PDPageContentStream cs = new PDPageContentStream(doc, page);
        addHeader(cs, "Advanced Tables", data);
        float curY = PAGE_HEIGHT - 150f;
        
        PDFont fontBold = fontHelveticaBold;
        float[] black = {0f,0f,0f};

        // KP System
        if (data.getKpEntries() != null && !data.getKpEntries().isEmpty()) {
            drawText(cs, "KP System (Sign Lord, Star Lord, Sub Lord):", MARGIN, curY, fontBold, 12, black);
            curY -= 20f;
            String[] kpHeaders = {"Element", "Longitude", "Sign Lord", "Star Lord", "Sub Lord"};
            float[] kpWidths = {100f, 100f, 100f, 100f, 100f};
            drawTableHeader(cs, kpHeaders, kpWidths, MARGIN, curY, 20f);
            curY -= 20f;
            boolean alt = false;
            for (KPSystem.KPEntry e : data.getKpEntries()) {
                if (curY < 100f) {
                    cs.close();
                    page = addPage(doc);
                    cs = new PDPageContentStream(doc, page);
                    addHeader(cs, "KP System (Contd.)", data);
                    curY = PAGE_HEIGHT - 150f;
                    drawTableHeader(cs, kpHeaders, kpWidths, MARGIN, curY, 20f);
                    curY -= 20f;
                }
                String[] row = {e.name(), KPSystem.formatLongitude(e.longitude()), e.signLord().getName(), e.starLord().getName(), e.subLord().getName()};
                drawTableRow(cs, row, kpWidths, MARGIN, curY, 20f, alt);
                curY -= 20f;
                alt = !alt;
            }
            curY -= 40f;
        }
        
        // Yogini Dasha
        if (data.getYoginiDashas() != null && !data.getYoginiDashas().isEmpty()) {
            if (curY < 200f) {
                cs.close();
                page = addPage(doc);
                cs = new PDPageContentStream(doc, page);
                addHeader(cs, "Yogini Dasha", data);
                curY = PAGE_HEIGHT - 150f;
            }
            drawText(cs, "Yogini Dasha (36-Year Cycle):", MARGIN, curY, fontBold, 12, black);
            curY -= 20f;
            String[] yHeaders = {"Dasha", "Planet Lord", "Start Date", "End Date"};
            float[] yWidths = {120f, 120f, 120f, 120f};
            drawTableHeader(cs, yHeaders, yWidths, MARGIN, curY, 20f);
            curY -= 20f;
            boolean alt = false;
            for (com.astrology.dasha.YoginiDasha.YoginiDashaPeriod y : data.getYoginiDashas()) {
                if (curY < 100f) {
                    cs.close();
                    page = addPage(doc);
                    cs = new PDPageContentStream(doc, page);
                    addHeader(cs, "Yogini Dasha (Contd.)", data);
                    curY = PAGE_HEIGHT - 150f;
                    drawTableHeader(cs, yHeaders, yWidths, MARGIN, curY, 20f);
                    curY -= 20f;
                }
                String[] row = {y.type().getName(), y.type().getLord().getName(), y.startDate().toString(), y.endDate().toString()};
                drawTableRow(cs, row, yWidths, MARGIN, curY, 20f, alt);
                curY -= 20f;
                alt = !alt;
            }
            curY -= 40f;
        }

        // Char Dasha
        if (data.getJaiminiDashas() != null && !data.getJaiminiDashas().isEmpty()) {
            if (curY < 200f) {
                cs.close();
                page = addPage(doc);
                cs = new PDPageContentStream(doc, page);
                addHeader(cs, "Jaimini Char Dasha", data);
                curY = PAGE_HEIGHT - 150f;
            }
            drawText(cs, "Jaimini Char Dasha:", MARGIN, curY, fontBold, 12, black);
            curY -= 20f;
            String[] cHeaders = {"Rashi", "Start Date", "End Date"};
            float[] cWidths = {160f, 160f, 160f};
            drawTableHeader(cs, cHeaders, cWidths, MARGIN, curY, 20f);
            curY -= 20f;
            boolean alt = false;
            for (com.astrology.dasha.CharDasha.CharDashaPeriod c : data.getJaiminiDashas()) {
                if (curY < 100f) {
                    cs.close();
                    page = addPage(doc);
                    cs = new PDPageContentStream(doc, page);
                    addHeader(cs, "Jaimini Char Dasha (Contd.)", data);
                    curY = PAGE_HEIGHT - 150f;
                    drawTableHeader(cs, cHeaders, cWidths, MARGIN, curY, 20f);
                    curY -= 20f;
                }
                String[] row = {c.rashi().getEnglishName(), c.startDate().toString(), c.endDate().toString()};
                drawTableRow(cs, row, cWidths, MARGIN, curY, 20f, alt);
                curY -= 20f;
                alt = !alt;
            }
            curY -= 40f;
        }
        
        // PAV
        if (data.getAshtakavarga() != null && data.getAshtakavarga().getPrastarashtakavarga() != null) {
            if (curY < 200f) {
                cs.close();
                page = addPage(doc);
                cs = new PDPageContentStream(doc, page);
                addHeader(cs, "Prastarashtakavarga (PAV)", data);
                curY = PAGE_HEIGHT - 150f;
            }
            drawText(cs, "Prastarashtakavarga (PAV) Grid:", MARGIN, curY, fontBold, 12, black);
            curY -= 20f;
            String[] pavHeaders = {"Planet", "Ar", "Ta", "Ge", "Ca", "Le", "Vi", "Li", "Sc", "Sg", "Cp", "Aq", "Pi"};
            float[] pavWidths = {60f, 35f, 35f, 35f, 35f, 35f, 35f, 35f, 35f, 35f, 35f, 35f, 35f};
            for (com.astrology.core.Planet p : data.getAshtakavarga().getPrastarashtakavarga().keySet()) {
                if (curY < 200f) {
                    cs.close();
                    page = addPage(doc);
                    cs = new PDPageContentStream(doc, page);
                    addHeader(cs, "Prastarashtakavarga (Contd.)", data);
                    curY = PAGE_HEIGHT - 150f;
                }
                drawText(cs, p.getName() + " PAV:", MARGIN, curY, fontBold, 10, black);
                curY -= 15f;
                drawTableHeader(cs, pavHeaders, pavWidths, MARGIN, curY, 15f);
                curY -= 15f;
                int[][] grid = data.getAshtakavarga().getPrastarashtakavarga().get(p);
                String[] contributors = {"Sun", "Moon", "Mars", "Merc", "Jup", "Ven", "Sat", "Asc"};
                for (int r = 0; r < 8; r++) {
                    String[] row = new String[13];
                    row[0] = contributors[r];
                    for (int c = 0; c < 12; c++) {
                        row[c+1] = String.valueOf(grid[r][c]);
                    }
                    drawTableRow(cs, row, pavWidths, MARGIN, curY, 15f, r%2!=0);
                    curY -= 15f;
                }
                curY -= 20f;
            }
        }
        cs.close();
    }
}
