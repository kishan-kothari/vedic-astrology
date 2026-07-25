package com.astrology.yoga.nabhasya;
import com.astrology.yoga.*;
import com.astrology.core.*;
import com.astrology.planets.BirthChart;
import java.util.List;
public class YavaYoga implements YogaRule {
    public String getYogaName() { return "YavaYoga"; }
    public Yoga.YogaCategory getCategory() { return Yoga.YogaCategory.NABHASYA_YOGA; }
    public Yoga check(BirthChart chart) {
        return new Yoga("YavaYoga", "YavaYoga", getCategory(), "Nabhasya Yoga", "Various effects", List.of(), List.of(), 1.0, true, "Formed");
    }
}
