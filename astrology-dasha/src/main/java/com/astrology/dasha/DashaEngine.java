package com.astrology.dasha;

import com.astrology.planets.BirthChart;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class DashaEngine {
    private final VimshottariDasha vimshottari = new VimshottariDasha();
    private final KalachakraDasha kalachakra = new KalachakraDasha();
    
    public enum DashaSystem { VIMSHOTTARI, YOGINI, JAIMINI_CHARA, KALACHAKRA }
    
    public List<DashaPeriod> calculate(BirthChart chart, DashaSystem system, int yearsAhead) {
        return switch (system) {
            case VIMSHOTTARI -> vimshottari.calculateMahadashas(chart, yearsAhead);
            case YOGINI -> throw new UnsupportedOperationException("Yogini Dasha uses specialized models. Call YoginiDasha class directly.");
            case JAIMINI_CHARA -> throw new UnsupportedOperationException("Jaimini Chara Dasha uses specialized models. Call CharDasha class directly.");
            case KALACHAKRA -> kalachakra.calculateKalachakraDashas(chart, yearsAhead);
        };
    }
    
    public DashaInfo getCurrentDasha(BirthChart chart, LocalDate asOfDate) {
        List<DashaPeriod> vimshottariDashas = vimshottari.calculateMahadashas(chart, 120);
        DashaPeriod md = vimshottari.getCurrentMahadasha(vimshottariDashas, asOfDate);
        if (md == null) return null;
        
        DashaPeriod ad = null;
        DashaPeriod pd = null;
        
        if (md.subPeriods() != null) {
            for (DashaPeriod sub : md.subPeriods()) {
                if (sub.isActive(asOfDate)) {
                    ad = sub;
                    break;
                }
            }
        }
        
        if (ad != null && ad.subPeriods() != null) {
            for (DashaPeriod sub : ad.subPeriods()) {
                if (sub.isActive(asOfDate)) {
                    pd = sub;
                    break;
                }
            }
        }
        
        String formatted = vimshottari.getDashaChain(vimshottariDashas, asOfDate);
        if (md != null && ad != null && pd != null) {
            formatted += String.format(" (%s to %s)", pd.startDate(), pd.endDate());
        }
        
        return new DashaInfo(
            md != null ? md.lord() : null,
            ad != null ? ad.lord() : null,
            pd != null ? pd.lord() : null,
            md != null ? md.startDate() : null, md != null ? md.endDate() : null,
            ad != null ? ad.startDate() : null, ad != null ? ad.endDate() : null,
            pd != null ? pd.startDate() : null, pd != null ? pd.endDate() : null,
            formatted
        );
    }
    
    public Map<DashaSystem, List<DashaPeriod>> calculateAll(BirthChart chart, int yearsAhead) {
        Map<DashaSystem, List<DashaPeriod>> map = new EnumMap<>(DashaSystem.class);
        for (DashaSystem sys : DashaSystem.values()) {
            map.put(sys, calculate(chart, sys, yearsAhead));
        }
        return map;
    }
}
