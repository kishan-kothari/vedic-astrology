package com.astrology.report;

import com.astrology.planets.BirthChart;
import com.astrology.core.Planet;
import com.astrology.divisional.DivisionalChartSet;
import com.astrology.dasha.DashaPeriod;
import com.astrology.yoga.Yoga;
import com.astrology.strength.ShadbalaResult;
import com.astrology.strength.AshtakavargaResult;
import com.astrology.transit.TransitChart;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Immutable container for all astrological report data.
 */
public class ReportData {
    private final BirthChart birthChart;
    private final DivisionalChartSet divisionalCharts;
    private final List<DashaPeriod> vimshottariDashas;
    private final List<com.astrology.dasha.YoginiDasha.YoginiDashaPeriod> yoginiDashas;
    private final List<com.astrology.dasha.CharDasha.CharDashaPeriod> jaiminiDashas;
    private final List<Yoga> yogas;
    private final Map<Planet, ShadbalaResult> shadbala;
    private final AshtakavargaResult ashtakavarga;
    private final TransitChart transitChart;
    private final String language;
    private final List<com.astrology.transit.SadeSatiCalculator.SadeSatiPhase> sadeSatiPhases;
    private final List<KPSystem.KPEntry> kpEntries;

    private ReportData(Builder builder) {
        this.birthChart = builder.birthChart;
        this.divisionalCharts = builder.divisionalCharts;
        this.vimshottariDashas = builder.vimshottariDashas != null ? Collections.unmodifiableList(builder.vimshottariDashas) : Collections.emptyList();
        this.yoginiDashas = builder.yoginiDashas != null ? Collections.unmodifiableList(builder.yoginiDashas) : Collections.emptyList();
        this.jaiminiDashas = builder.jaiminiDashas != null ? Collections.unmodifiableList(builder.jaiminiDashas) : Collections.emptyList();
        this.yogas = builder.yogas != null ? Collections.unmodifiableList(builder.yogas) : Collections.emptyList();
        this.shadbala = builder.shadbala != null ? Collections.unmodifiableMap(builder.shadbala) : Collections.emptyMap();
        this.ashtakavarga = builder.ashtakavarga;
        this.transitChart = builder.transitChart;
        this.language = builder.language != null ? builder.language : "en";
        this.sadeSatiPhases = builder.sadeSatiPhases != null ? Collections.unmodifiableList(builder.sadeSatiPhases) : Collections.emptyList();
        this.kpEntries = builder.kpEntries != null ? Collections.unmodifiableList(builder.kpEntries) : Collections.emptyList();
    }

    public BirthChart getBirthChart() { return birthChart; }
    public DivisionalChartSet getDivisionalCharts() { return divisionalCharts; }
    public List<DashaPeriod> getVimshottariDashas() { return vimshottariDashas; }
    public List<com.astrology.dasha.YoginiDasha.YoginiDashaPeriod> getYoginiDashas() { return yoginiDashas; }
    public List<com.astrology.dasha.CharDasha.CharDashaPeriod> getJaiminiDashas() { return jaiminiDashas; }
    public List<Yoga> getYogas() { return yogas; }
    public Map<Planet, ShadbalaResult> getShadbala() { return shadbala; }
    public AshtakavargaResult getAshtakavarga() { return ashtakavarga; }
    public TransitChart getTransitChart() { return transitChart; }
    public String getLanguage() { return language; }
    public List<com.astrology.transit.SadeSatiCalculator.SadeSatiPhase> getSadeSatiPhases() { return sadeSatiPhases; }
    public List<KPSystem.KPEntry> getKpEntries() { return kpEntries; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private BirthChart birthChart;
        private DivisionalChartSet divisionalCharts;
        private List<DashaPeriod> vimshottariDashas;
        private List<com.astrology.dasha.YoginiDasha.YoginiDashaPeriod> yoginiDashas;
        private List<com.astrology.dasha.CharDasha.CharDashaPeriod> jaiminiDashas;
        private List<Yoga> yogas;
        private Map<Planet, ShadbalaResult> shadbala;
        private AshtakavargaResult ashtakavarga;
        private TransitChart transitChart;
        private String language;
        private List<com.astrology.transit.SadeSatiCalculator.SadeSatiPhase> sadeSatiPhases;
        private List<KPSystem.KPEntry> kpEntries;

        public Builder birthChart(BirthChart birthChart) {
            this.birthChart = birthChart;
            return this;
        }

        public Builder divisionalCharts(DivisionalChartSet divisionalCharts) {
            this.divisionalCharts = divisionalCharts;
            return this;
        }

        public Builder vimshottariDashas(List<DashaPeriod> vimshottariDashas) {
            this.vimshottariDashas = vimshottariDashas;
            return this;
        }

        public Builder yoginiDashas(List<com.astrology.dasha.YoginiDasha.YoginiDashaPeriod> yoginiDashas) {
            this.yoginiDashas = yoginiDashas;
            return this;
        }

        public Builder jaiminiDashas(List<com.astrology.dasha.CharDasha.CharDashaPeriod> jaiminiDashas) {
            this.jaiminiDashas = jaiminiDashas;
            return this;
        }

        public Builder yogas(List<Yoga> yogas) {
            this.yogas = yogas;
            return this;
        }

        public Builder shadbala(Map<Planet, ShadbalaResult> shadbala) {
            this.shadbala = shadbala;
            return this;
        }

        public Builder ashtakavarga(AshtakavargaResult ashtakavarga) {
            this.ashtakavarga = ashtakavarga;
            return this;
        }

        public Builder transitChart(TransitChart transitChart) {
            this.transitChart = transitChart;
            return this;
        }

        public Builder language(String language) {
            this.language = language;
            return this;
        }

        public Builder sadeSatiPhases(List<com.astrology.transit.SadeSatiCalculator.SadeSatiPhase> sadeSatiPhases) {
            this.sadeSatiPhases = sadeSatiPhases;
            return this;
        }

        public Builder kpEntries(List<KPSystem.KPEntry> kpEntries) {
            this.kpEntries = kpEntries;
            return this;
        }

        public ReportData build() {
            return new ReportData(this);
        }
    }
}
