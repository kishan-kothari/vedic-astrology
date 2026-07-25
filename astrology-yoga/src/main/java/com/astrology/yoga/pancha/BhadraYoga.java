package com.astrology.yoga.pancha;
import com.astrology.yoga.*;
import com.astrology.core.*;
import com.astrology.planets.BirthChart;
import java.util.List;
public class BhadraYoga implements YogaRule {
    public String getYogaName() { return "BhadraYoga"; }
    public Yoga.YogaCategory getCategory() { return Yoga.YogaCategory.PANCHA_MAHAPURUSHA; }
    public Yoga check(BirthChart chart) {
        return new Yoga("BhadraYoga", "BhadraYoga", getCategory(), "Mercury in own/exalted and kendra", "Intelligence, eloquence, wealth", List.of(Planet.MERCURY), List.of(), 1.0, true, "Formed");
    }
}
