package com.astrology.yoga.naksatra;
import com.astrology.yoga.*;
import com.astrology.core.*;
import com.astrology.planets.BirthChart;
import java.util.List;
public class VasumatiYoga implements YogaRule {
    public String getYogaName() { return "VasumatiYoga"; }
    public Yoga.YogaCategory getCategory() { return Yoga.YogaCategory.NAKSATRA_YOGA; }
    public Yoga check(BirthChart chart) {
        return new Yoga("VasumatiYoga", "VasumatiYoga", getCategory(), "Naksatra Yoga", "Various effects", List.of(), List.of(), 1.0, true, "Formed");
    }
}
