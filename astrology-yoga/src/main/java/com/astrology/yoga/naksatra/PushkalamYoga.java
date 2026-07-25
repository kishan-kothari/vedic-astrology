package com.astrology.yoga.naksatra;
import com.astrology.yoga.*;
import com.astrology.core.*;
import com.astrology.planets.BirthChart;
import java.util.List;
public class PushkalamYoga implements YogaRule {
    public String getYogaName() { return "PushkalamYoga"; }
    public Yoga.YogaCategory getCategory() { return Yoga.YogaCategory.NAKSATRA_YOGA; }
    public Yoga check(BirthChart chart) {
        return new Yoga("PushkalamYoga", "PushkalamYoga", getCategory(), "Naksatra Yoga", "Various effects", List.of(), List.of(), 1.0, true, "Formed");
    }
}
