package com.astrology.divisional;

import com.astrology.core.Planet;
import com.astrology.planets.BirthChart;

import java.util.Map;
import java.util.HashMap;

public class DivisionalChartSet {
    private final BirthChart natalChart;
    private final Map<Integer, DivisionalChart> charts;
    
    public DivisionalChartSet(BirthChart natalChart, Map<Integer, DivisionalChart> charts) {
        this.natalChart = natalChart;
        this.charts = new HashMap<>(charts);
    }
    
    public DivisionalChart getD1() { return getChart(1); }
    public DivisionalChart getD2() { return getChart(2); }
    public DivisionalChart getD3() { return getChart(3); }
    public DivisionalChart getD4() { return getChart(4); }
    public DivisionalChart getD7() { return getChart(7); }
    public DivisionalChart getD9() { return getChart(9); }
    public DivisionalChart getD10() { return getChart(10); }
    public DivisionalChart getD12() { return getChart(12); }
    public DivisionalChart getD16() { return getChart(16); }
    public DivisionalChart getD20() { return getChart(20); }
    public DivisionalChart getD24() { return getChart(24); }
    public DivisionalChart getD27() { return getChart(27); }
    public DivisionalChart getD30() { return getChart(30); }
    public DivisionalChart getD40() { return getChart(40); }
    public DivisionalChart getD45() { return getChart(45); }
    public DivisionalChart getD60() { return getChart(60); }
    
    public DivisionalChart getChart(int divisor) {
        return charts.get(divisor);
    }

    public BirthChart getNatalChart() {
        return natalChart;
    }

    public Map<Planet, Double> calculateVimshopakaBala() {
        return VimshopakaBala.calculate(this, null);
    }
}
