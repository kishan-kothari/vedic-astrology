package com.astrology.yoga;

import com.astrology.planets.BirthChart;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class YogaEngine {
    private final List<YogaRule> rules;
    
    public YogaEngine() {
        rules = List.of(
            new com.astrology.yoga.pancha.RuchakaYoga(), new com.astrology.yoga.pancha.BhadraYoga(), new com.astrology.yoga.pancha.HamsaYoga(), new com.astrology.yoga.pancha.MalavyaYoga(), new com.astrology.yoga.pancha.SasaYoga(), new com.astrology.yoga.raja.RajaYoga1(), new com.astrology.yoga.raja.RajaYoga2(), new com.astrology.yoga.raja.RajaYoga3(), new com.astrology.yoga.raja.RajaYoga4(), new com.astrology.yoga.raja.RajaYoga5(), new com.astrology.yoga.raja.RajaYoga6(), new com.astrology.yoga.raja.RajaYoga7(), new com.astrology.yoga.raja.RajaYoga8(), new com.astrology.yoga.raja.RajaYoga9(), new com.astrology.yoga.raja.RajaYoga10(), new com.astrology.yoga.raja.RajaYoga11(), new com.astrology.yoga.raja.RajaYoga12(), new com.astrology.yoga.raja.RajaYoga13(), new com.astrology.yoga.raja.RajaYoga14(), new com.astrology.yoga.raja.RajaYoga15(), new com.astrology.yoga.raja.RajaYoga16(), new com.astrology.yoga.raja.RajaYoga17(), new com.astrology.yoga.raja.RajaYoga18(), new com.astrology.yoga.raja.RajaYoga19(), new com.astrology.yoga.raja.RajaYoga20(), new com.astrology.yoga.raja.DharmakarmadhipatiYoga(), new com.astrology.yoga.raja.MahabhagyaYoga(), new com.astrology.yoga.raja.MahaparivartanaYoga(), new com.astrology.yoga.dhana.DhanaYoga1(), new com.astrology.yoga.dhana.DhanaYoga2(), new com.astrology.yoga.dhana.DhanaYoga3(), new com.astrology.yoga.dhana.DhanaYoga4(), new com.astrology.yoga.dhana.DhanaYoga5(), new com.astrology.yoga.dhana.DhanaYoga6(), new com.astrology.yoga.dhana.DhanaYoga7(), new com.astrology.yoga.dhana.DhanaYoga8(), new com.astrology.yoga.dhana.DhanaYoga9(), new com.astrology.yoga.dhana.DhanaYoga10(), new com.astrology.yoga.dhana.KuberaYoga(), new com.astrology.yoga.viparita.HarshaYoga(), new com.astrology.yoga.viparita.SaralaYoga(), new com.astrology.yoga.viparita.VimalaYoga(), new com.astrology.yoga.lunar.GajakesariYoga(), new com.astrology.yoga.lunar.SunaphaYoga(), new com.astrology.yoga.lunar.AnaphaYoga(), new com.astrology.yoga.lunar.DurudhuraYoga(), new com.astrology.yoga.lunar.KemdrumaYoga(), new com.astrology.yoga.lunar.Chandra_MangalaYoga(), new com.astrology.yoga.solar.VesiYoga(), new com.astrology.yoga.solar.VasiYoga(), new com.astrology.yoga.solar.ObhayachariYoga(), new com.astrology.yoga.solar.BudhAdityaYoga(), new com.astrology.yoga.solar.SunSaturnYoga(), new com.astrology.yoga.nabhasya.RajjuYoga(), new com.astrology.yoga.nabhasya.MusalaYoga(), new com.astrology.yoga.nabhasya.NalaYoga(), new com.astrology.yoga.nabhasya.MalaYoga(), new com.astrology.yoga.nabhasya.SarpaYoga(), new com.astrology.yoga.nabhasya.GadaYoga(), new com.astrology.yoga.nabhasya.ShringatakaYoga(), new com.astrology.yoga.nabhasya.HalaYoga(), new com.astrology.yoga.nabhasya.VajraYoga(), new com.astrology.yoga.nabhasya.YavaYoga(), new com.astrology.yoga.nabhasya.KamalaYoga(), new com.astrology.yoga.nabhasya.VapiYoga(), new com.astrology.yoga.nabhasya.VeenaYoga(), new com.astrology.yoga.nabhasya.DaminiYoga(), new com.astrology.yoga.nabhasya.PashaYoga(), new com.astrology.yoga.nabhasya.KedaraYoga(), new com.astrology.yoga.nabhasya.ShoolaYoga(), new com.astrology.yoga.nabhasya.YugaYoga(), new com.astrology.yoga.nabhasya.GolaYoga(), new com.astrology.yoga.special.SaraswatiYoga(), new com.astrology.yoga.special.KalanidanaYoga(), new com.astrology.yoga.special.HamsapadasYoga(), new com.astrology.yoga.special.AmalaYoga(), new com.astrology.yoga.special.MridangaYoga(), new com.astrology.yoga.special.SankhaYoga(), new com.astrology.yoga.special.ParvataYoga(), new com.astrology.yoga.special.KahalaPhYoga(), new com.astrology.yoga.naksatra.VasumatiYoga(), new com.astrology.yoga.naksatra.PushkalamYoga(), new com.astrology.yoga.dosha.MangalDoshYoga(), new com.astrology.yoga.dosha.KaalSarpaYoga(), new com.astrology.yoga.dosha.ShakatYoga(), new com.astrology.yoga.dosha.DaridraYoga(), new com.astrology.yoga.dosha.GraahaYuddhaYoga()
        );
    }
    
    public List<Yoga> detectAllYogas(BirthChart chart) {
        return rules.stream().map(r -> r.check(chart)).collect(Collectors.toList());
    }
    
    public List<Yoga> detectYogasByCategory(BirthChart chart, Yoga.YogaCategory category) {
        return rules.stream()
            .filter(r -> r.getCategory() == category)
            .map(r -> r.check(chart))
            .collect(Collectors.toList());
    }
    
    public List<Yoga> getPresentYogas(BirthChart chart) {
        return detectAllYogas(chart).stream().filter(Yoga::isPresent).collect(Collectors.toList());
    }
    
    public Map<Yoga.YogaCategory, List<Yoga>> detectGrouped(BirthChart chart) {
        return getPresentYogas(chart).stream().collect(Collectors.groupingBy(Yoga::category));
    }
}
