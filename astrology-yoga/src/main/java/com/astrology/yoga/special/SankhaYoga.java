package com.astrology.yoga.special;
import com.astrology.yoga.*;
import com.astrology.core.*;
import com.astrology.planets.BirthChart;
import java.util.List;
public class SankhaYoga implements YogaRule {
    public String getYogaName() { return "SankhaYoga"; }
    public Yoga.YogaCategory getCategory() { return Yoga.YogaCategory.SPECIAL_YOGA; }
    public Yoga check(BirthChart chart) {
        return new Yoga("SankhaYoga", "SankhaYoga", getCategory(), "Special Yoga", "Various effects", List.of(), List.of(), 1.0, true, "Formed");
    }
}
