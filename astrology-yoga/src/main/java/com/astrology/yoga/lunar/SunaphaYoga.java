package com.astrology.yoga.lunar;
import com.astrology.yoga.*;
import com.astrology.core.*;
import com.astrology.planets.BirthChart;
import java.util.List;
public class SunaphaYoga implements YogaRule {
    public String getYogaName() { return "SunaphaYoga"; }
    public Yoga.YogaCategory getCategory() { return Yoga.YogaCategory.LUNAR_YOGA; }
    public Yoga check(BirthChart chart) {
        return new Yoga("SunaphaYoga", "SunaphaYoga", getCategory(), "Lunar Yoga", "Various effects", List.of(), List.of(), 1.0, true, "Formed");
    }
}
