package com.astrology.yoga.special;
import com.astrology.yoga.*;
import com.astrology.core.*;
import com.astrology.planets.BirthChart;
import java.util.List;
public class AmalaYoga implements YogaRule {
    public String getYogaName() { return "AmalaYoga"; }
    public Yoga.YogaCategory getCategory() { return Yoga.YogaCategory.SPECIAL_YOGA; }
    public Yoga check(BirthChart chart) {
        return new Yoga("AmalaYoga", "AmalaYoga", getCategory(), "Special Yoga", "Various effects", List.of(), List.of(), 1.0, true, "Formed");
    }
}
