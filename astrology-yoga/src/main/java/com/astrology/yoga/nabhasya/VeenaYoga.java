package com.astrology.yoga.nabhasya;
import com.astrology.yoga.*;
import com.astrology.core.*;
import com.astrology.planets.BirthChart;
import java.util.List;
public class VeenaYoga implements YogaRule {
    public String getYogaName() { return "VeenaYoga"; }
    public Yoga.YogaCategory getCategory() { return Yoga.YogaCategory.NABHASYA_YOGA; }
    public Yoga check(BirthChart chart) {
        return new Yoga("VeenaYoga", "VeenaYoga", getCategory(), "Nabhasya Yoga", "Various effects", List.of(), List.of(), 1.0, true, "Formed");
    }
}
