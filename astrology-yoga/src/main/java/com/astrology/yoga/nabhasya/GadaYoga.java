package com.astrology.yoga.nabhasya;
import com.astrology.yoga.*;
import com.astrology.core.*;
import com.astrology.planets.BirthChart;
import java.util.List;
public class GadaYoga implements YogaRule {
    public String getYogaName() { return "GadaYoga"; }
    public Yoga.YogaCategory getCategory() { return Yoga.YogaCategory.NABHASYA_YOGA; }
    public Yoga check(BirthChart chart) {
        return new Yoga("GadaYoga", "GadaYoga", getCategory(), "Nabhasya Yoga", "Various effects", List.of(), List.of(), 1.0, true, "Formed");
    }
}
