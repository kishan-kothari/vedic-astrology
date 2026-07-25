package com.astrology.yoga.solar;
import com.astrology.yoga.*;
import com.astrology.core.*;
import com.astrology.planets.BirthChart;
import java.util.List;
public class VasiYoga implements YogaRule {
    public String getYogaName() { return "VasiYoga"; }
    public Yoga.YogaCategory getCategory() { return Yoga.YogaCategory.SOLAR_YOGA; }
    public Yoga check(BirthChart chart) {
        return new Yoga("VasiYoga", "VasiYoga", getCategory(), "Solar Yoga", "Various effects", List.of(), List.of(), 1.0, true, "Formed");
    }
}
