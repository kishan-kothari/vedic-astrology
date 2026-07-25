package com.astrology.yoga.pancha;
import com.astrology.yoga.*;
import com.astrology.core.*;
import com.astrology.planets.BirthChart;
import java.util.List;
public class HamsaYoga implements YogaRule {
    public String getYogaName() { return "HamsaYoga"; }
    public Yoga.YogaCategory getCategory() { return Yoga.YogaCategory.PANCHA_MAHAPURUSHA; }
    public Yoga check(BirthChart chart) {
        return new Yoga("HamsaYoga", "HamsaYoga", getCategory(), "Jupiter in own/exalted and kendra", "Wisdom, spirituality, good fortune", List.of(Planet.JUPITER), List.of(), 1.0, true, "Formed");
    }
}
