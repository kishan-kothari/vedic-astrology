package com.astrology.yoga.pancha;
import com.astrology.yoga.*;
import com.astrology.core.*;
import com.astrology.planets.BirthChart;
import java.util.List;
public class MalavyaYoga implements YogaRule {
    public String getYogaName() { return "MalavyaYoga"; }
    public Yoga.YogaCategory getCategory() { return Yoga.YogaCategory.PANCHA_MAHAPURUSHA; }
    public Yoga check(BirthChart chart) {
        return new Yoga("MalavyaYoga", "MalavyaYoga", getCategory(), "Venus in own/exalted and kendra", "Beauty, luxury, artistic talent", List.of(Planet.VENUS), List.of(), 1.0, true, "Formed");
    }
}
