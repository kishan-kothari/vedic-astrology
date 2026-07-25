package com.astrology.yoga.lunar;
import com.astrology.yoga.*;
import com.astrology.core.*;
import com.astrology.planets.BirthChart;
import java.util.List;
public class Chandra_MangalaYoga implements YogaRule {
    public String getYogaName() { return "Chandra_MangalaYoga"; }
    public Yoga.YogaCategory getCategory() { return Yoga.YogaCategory.LUNAR_YOGA; }
    public Yoga check(BirthChart chart) {
        return new Yoga("Chandra_MangalaYoga", "Chandra_MangalaYoga", getCategory(), "Lunar Yoga", "Various effects", List.of(), List.of(), 1.0, true, "Formed");
    }
}
