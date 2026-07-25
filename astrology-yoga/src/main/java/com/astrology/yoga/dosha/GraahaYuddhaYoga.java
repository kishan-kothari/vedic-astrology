package com.astrology.yoga.dosha;
import com.astrology.yoga.*;
import com.astrology.core.*;
import com.astrology.planets.BirthChart;
import java.util.List;
public class GraahaYuddhaYoga implements YogaRule {
    public String getYogaName() { return "GraahaYuddhaYoga"; }
    public Yoga.YogaCategory getCategory() { return Yoga.YogaCategory.DOSHA_YOGA; }
    public Yoga check(BirthChart chart) {
        return new Yoga("GraahaYuddhaYoga", "GraahaYuddhaYoga", getCategory(), "Dosha Yoga", "Negative effects", List.of(), List.of(), 1.0, true, "Formed");
    }
}
