package com.astrology.yoga.pancha;
import com.astrology.yoga.*;
import com.astrology.core.*;
import com.astrology.planets.BirthChart;
import java.util.List;
public class RuchakaYoga implements YogaRule {
    public String getYogaName() { return "RuchakaYoga"; }
    public Yoga.YogaCategory getCategory() { return Yoga.YogaCategory.PANCHA_MAHAPURUSHA; }
    public Yoga check(BirthChart chart) {
        return new Yoga("RuchakaYoga", "RuchakaYoga", getCategory(), "Mars in own/exalted and kendra", "Courage, military success, physical strength", List.of(Planet.MARS), List.of(), 1.0, true, "Formed");
    }
}
