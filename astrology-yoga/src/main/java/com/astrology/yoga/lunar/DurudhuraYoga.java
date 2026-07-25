package com.astrology.yoga.lunar;
import com.astrology.yoga.*;
import com.astrology.core.*;
import com.astrology.planets.BirthChart;
import java.util.List;
public class DurudhuraYoga implements YogaRule {
    public String getYogaName() { return "DurudhuraYoga"; }
    public Yoga.YogaCategory getCategory() { return Yoga.YogaCategory.LUNAR_YOGA; }
    public Yoga check(BirthChart chart) {
        return new Yoga("DurudhuraYoga", "DurudhuraYoga", getCategory(), "Lunar Yoga", "Various effects", List.of(), List.of(), 1.0, true, "Formed");
    }
}
