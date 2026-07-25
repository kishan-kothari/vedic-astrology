package com.astrology.yoga.pancha;
import com.astrology.yoga.*;
import com.astrology.core.*;
import com.astrology.planets.BirthChart;
import java.util.List;
public class SasaYoga implements YogaRule {
    public String getYogaName() { return "SasaYoga"; }
    public Yoga.YogaCategory getCategory() { return Yoga.YogaCategory.PANCHA_MAHAPURUSHA; }
    public Yoga check(BirthChart chart) {
        return new Yoga("SasaYoga", "SasaYoga", getCategory(), "Saturn in own/exalted and kendra", "Authority, discipline, long life", List.of(Planet.SATURN), List.of(), 1.0, true, "Formed");
    }
}
