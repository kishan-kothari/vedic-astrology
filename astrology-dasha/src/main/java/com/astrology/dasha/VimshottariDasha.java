package com.astrology.dasha;

import com.astrology.core.Planet;
import com.astrology.planets.BirthChart;
import com.astrology.planets.PlanetPosition;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.astrology.core.Planet.*;
import static com.astrology.dasha.DashaPeriod.DashaType.*;

public class VimshottariDasha {
    private static final Planet[] SEQUENCE = {KETU, VENUS, SUN, MOON, MARS, RAHU, JUPITER, SATURN, MERCURY};
    private static final double[] YEARS = {7, 20, 6, 10, 7, 18, 16, 19, 17};
    private static final double TOTAL_YEARS = 120.0;
    
    public List<DashaPeriod> calculateMahadashas(BirthChart chart, int yearsAhead) {
        DashaStart start = calculateDashaStart(chart);
        LocalDate currentDate = chart.getBirthData().localDateTime().toLocalDate();
        LocalDate endDateLimit = currentDate.plusYears(yearsAhead);
        
        List<DashaPeriod> mahadashas = new ArrayList<>();
        
        int currentIndex = start.startIndex;
        double balanceYears = start.balanceYears;
        
        Planet lord = SEQUENCE[currentIndex];
        LocalDate endDate = addYears(currentDate, balanceYears);
        List<DashaPeriod> antardashas = calculateAntardashas(lord, YEARS[currentIndex], currentDate, endDate, true, balanceYears);
        
        mahadashas.add(new DashaPeriod(lord, currentDate, endDate, ChronoUnit.DAYS.between(currentDate, endDate), antardashas, MAHADASHA));
        currentDate = endDate;
        currentIndex = (currentIndex + 1) % 9;
        
        while (currentDate.isBefore(endDateLimit)) {
            lord = SEQUENCE[currentIndex];
            double mdYears = YEARS[currentIndex];
            endDate = addYears(currentDate, mdYears);
            antardashas = calculateAntardashas(lord, mdYears, currentDate, endDate, false, mdYears);
            mahadashas.add(new DashaPeriod(lord, currentDate, endDate, ChronoUnit.DAYS.between(currentDate, endDate), antardashas, MAHADASHA));
            
            currentDate = endDate;
            currentIndex = (currentIndex + 1) % 9;
        }
        
        return mahadashas;
    }
    
    private DashaStart calculateDashaStart(BirthChart chart) {
        PlanetPosition moonPos = chart.getPlanet(MOON);
        double moonLon = moonPos.siderealLongitude();
        
        int nakshatra = (int) Math.floor(moonLon / (360.0 / 27.0));
        double elapsed = moonLon % (360.0 / 27.0);
        double remaining = (360.0 / 27.0) - elapsed;
        
        int sequenceIndex = nakshatra % 9;
        double dashaBalance = (remaining / (360.0 / 27.0)) * YEARS[sequenceIndex];
        
        return new DashaStart(sequenceIndex, dashaBalance);
    }
    
    private List<DashaPeriod> calculateAntardashas(Planet mahadasha, double mdYears, LocalDate mdStart, LocalDate mdEnd, boolean isFirst, double balanceYears) {
        List<DashaPeriod> result = new ArrayList<>();
        int startIndex = 0;
        for (int i = 0; i < 9; i++) {
            if (SEQUENCE[i] == mahadasha) {
                startIndex = i;
                break;
            }
        }
        
        LocalDate current = mdStart;
        double consumed = mdYears - balanceYears;
        
        int i = startIndex;
        int count = 0;
        
        while (count < 9) {
            Planet lord = SEQUENCE[i];
            double adYearsFull = (mdYears * YEARS[i]) / TOTAL_YEARS;
            
            double adYearsActual = adYearsFull;
            if (isFirst) {
                if (consumed >= adYearsFull) {
                    consumed -= adYearsFull;
                    i = (i + 1) % 9;
                    count++;
                    continue;
                } else if (consumed > 0) {
                    adYearsActual = adYearsFull - consumed;
                    consumed = 0;
                }
            }
            
            LocalDate next = addYears(current, adYearsActual);
            if (next.isAfter(mdEnd) || count == 8) next = mdEnd; 
            
            List<DashaPeriod> pd = calculatePratyantardashas(mahadasha, lord, adYearsFull, current, next);
            result.add(new DashaPeriod(lord, current, next, ChronoUnit.DAYS.between(current, next), pd, ANTARDASHA));
            
            current = next;
            i = (i + 1) % 9;
            count++;
        }
        
        return result;
    }
    
    private List<DashaPeriod> calculatePratyantardashas(Planet md, Planet ad, double pdYearsParent, LocalDate adStart, LocalDate adEnd) {
        List<DashaPeriod> result = new ArrayList<>();
        int startIndex = 0;
        for (int i = 0; i < 9; i++) {
            if (SEQUENCE[i] == ad) {
                startIndex = i;
                break;
            }
        }
        
        LocalDate current = adStart;
        int count = 0;
        int i = startIndex;
        double totalSpanDays = ChronoUnit.DAYS.between(adStart, adEnd);
        double currentDays = 0;
        
        while (count < 9) {
            Planet lord = SEQUENCE[i];
            double pdDurationYears = (pdYearsParent * YEARS[i]) / TOTAL_YEARS;
            double pdDays = (pdDurationYears / pdYearsParent) * totalSpanDays;
            
            currentDays += pdDays;
            LocalDate next = adStart.plusDays(Math.round(currentDays));
            if (count == 8) next = adEnd;
            
            List<DashaPeriod> sookshma = calculateSookshma(md, ad, lord, pdDurationYears, current, next);
            result.add(new DashaPeriod(lord, current, next, ChronoUnit.DAYS.between(current, next), sookshma, PRATYANTARDASHA));
            
            current = next;
            i = (i + 1) % 9;
            count++;
        }
        return result;
    }
    
    private List<DashaPeriod> calculateSookshma(Planet md, Planet ad, Planet pd, double sYearsParent, LocalDate pdStart, LocalDate pdEnd) {
        List<DashaPeriod> result = new ArrayList<>();
        int startIndex = 0;
        for (int i = 0; i < 9; i++) if (SEQUENCE[i] == pd) startIndex = i;
        
        LocalDate current = pdStart;
        int count = 0;
        int i = startIndex;
        double totalSpanDays = ChronoUnit.DAYS.between(pdStart, pdEnd);
        double currentDays = 0;
        
        while (count < 9) {
            Planet lord = SEQUENCE[i];
            double sDurationYears = (sYearsParent * YEARS[i]) / TOTAL_YEARS;
            double sDays = (sDurationYears / sYearsParent) * totalSpanDays;
            
            currentDays += sDays;
            LocalDate next = pdStart.plusDays(Math.round(currentDays));
            if (count == 8) next = pdEnd;
            
            List<DashaPeriod> prana = calculatePrana(md, ad, pd, lord, sDurationYears, current, next);
            result.add(new DashaPeriod(lord, current, next, ChronoUnit.DAYS.between(current, next), prana, SOOKSHMA));
            
            current = next;
            i = (i + 1) % 9;
            count++;
        }
        return result;
    }
    
    private List<DashaPeriod> calculatePrana(Planet md, Planet ad, Planet pd, Planet s, double pYearsParent, LocalDate sStart, LocalDate sEnd) {
        List<DashaPeriod> result = new ArrayList<>();
        int startIndex = 0;
        for (int i = 0; i < 9; i++) if (SEQUENCE[i] == s) startIndex = i;
        
        LocalDate current = sStart;
        int count = 0;
        int i = startIndex;
        double totalSpanDays = ChronoUnit.DAYS.between(sStart, sEnd);
        double currentDays = 0;
        
        while (count < 9) {
            Planet lord = SEQUENCE[i];
            double pDurationYears = (pYearsParent * YEARS[i]) / TOTAL_YEARS;
            double pDays = (pDurationYears / pYearsParent) * totalSpanDays;
            
            currentDays += pDays;
            LocalDate next = sStart.plusDays(Math.round(currentDays));
            if (count == 8) next = sEnd;
            
            result.add(new DashaPeriod(lord, current, next, ChronoUnit.DAYS.between(current, next), null, PRANA));
            
            current = next;
            i = (i + 1) % 9;
            count++;
        }
        return result;
    }
    
    public DashaPeriod getCurrentMahadasha(List<DashaPeriod> mahadashas, LocalDate date) {
        for (DashaPeriod md : mahadashas) {
            if (md.isActive(date)) return md;
        }
        return null;
    }
    
    public DashaPeriod getCurrentAntardasha(List<DashaPeriod> mahadashas, LocalDate date) {
        DashaPeriod md = getCurrentMahadasha(mahadashas, date);
        if (md != null && md.subPeriods() != null) {
            for (DashaPeriod ad : md.subPeriods()) {
                if (ad.isActive(date)) return ad;
            }
        }
        return null;
    }
    
    public String getDashaChain(List<DashaPeriod> mahadashas, LocalDate date) {
        DashaPeriod md = getCurrentMahadasha(mahadashas, date);
        if (md == null) return "Unknown";
        StringBuilder sb = new StringBuilder(md.lord().name());
        
        DashaPeriod current = md;
        while (current.subPeriods() != null && !current.subPeriods().isEmpty()) {
            boolean found = false;
            for (DashaPeriod sub : current.subPeriods()) {
                if (sub.isActive(date)) {
                    sb.append("-").append(sub.lord().name());
                    current = sub;
                    found = true;
                    break;
                }
            }
            if (!found) break;
        }
        return sb.toString();
    }
    
    private LocalDate addYears(LocalDate date, double years) {
        long daysToAdd = (long) Math.round(years * 365.25);
        return date.plusDays(daysToAdd);
    }
    
    private record DashaStart(int startIndex, double balanceYears) {}
}
