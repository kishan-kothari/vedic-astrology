package com.astrology.report;

import com.astrology.core.Planet;
import com.astrology.dasha.DashaPeriod;
import com.astrology.divisional.DivisionalChart;
import com.astrology.divisional.DivisionalChartSet;
import com.astrology.planets.BirthChart;

import java.util.ArrayList;
import java.util.List;

public class DivisionalTimingEngine {

    public record TimingEvent(String periodName, String startDate, String endDate, String description) {}

    public static List<TimingEvent> calculatePositiveTimings(int divisor, DivisionalChartSet chartSet, List<DashaPeriod> dashas, String lang) {
        List<TimingEvent> events = new ArrayList<>();
        DivisionalChart dChart = chartSet.getChart(divisor);
        if (dChart == null) return events;

        // Simplified logic: Positive timings are associated with the Mahadasha/Antardasha 
        // of the ascendant lord of the specific divisional chart.
        Planet dLagnaLord = dChart.lagnaRashi().getLord();
        
        java.time.LocalDate birthDate = dashas.get(0).startDate();
        java.time.LocalDate today = java.time.LocalDate.now();
        
        for (DashaPeriod md : dashas) {
            if (md.lord() == dLagnaLord) {
                // Filter realistic age for event (e.g. D7 for children > 20 years old, D10 for career > 18)
                long ageAtEvent = java.time.temporal.ChronoUnit.YEARS.between(birthDate, md.startDate());
                if ((divisor == 7 && ageAtEvent < 20) || (divisor == 10 && ageAtEvent < 18)) continue;
                
                String desc = "hi".equalsIgnoreCase(lang) 
                    ? "महादशा (" + md.lord().getName() + "): इस अवधि में D" + divisor + " से सम्बंधित उत्कृष्ट परिणाम प्राप्त होंगे।" 
                    : "Mahadasha (" + md.lord().getName() + "): Excellent results related to D" + divisor + " expected during this period.";
                events.add(new TimingEvent(md.lord().getName() + " Mahadasha", md.startDate().toString(), md.endDate().toString(), desc));
            }

            for (DashaPeriod ad : md.subPeriods()) {
                if (ad.lord() == dLagnaLord) {
                    long ageAtEvent = java.time.temporal.ChronoUnit.YEARS.between(birthDate, ad.startDate());
                    if ((divisor == 7 && ageAtEvent < 20) || (divisor == 10 && ageAtEvent < 18)) continue;
                    // Only show future/recent Antardashas to keep report actionable
                    if (ad.endDate().isBefore(today.minusYears(1))) continue;
                    
                    String desc = "hi".equalsIgnoreCase(lang)
                        ? "अंतर्दशा (" + ad.lord().getName() + "): इस अवधि में D" + divisor + " के मामलों में सकारात्मक बदलाव आएँगे।"
                        : "Antardasha (" + ad.lord().getName() + "): Positive developments regarding D" + divisor + " matters.";
                    events.add(new TimingEvent(md.lord().getName() + "-" + ad.lord().getName(), ad.startDate().toString(), ad.endDate().toString(), desc));
                }
            }
        }
        
        return events;
    }
}
