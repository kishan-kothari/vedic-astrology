package com.astrology.yoga.dhana;
import com.astrology.yoga.*;
import com.astrology.core.*;
import com.astrology.planets.BirthChart;
import java.util.List;
public class DhanaYoga8 implements YogaRule {
    public String getYogaName() { return "DhanaYoga8"; }
    public Yoga.YogaCategory getCategory() { return Yoga.YogaCategory.DHANA_YOGA; }
    public Yoga check(BirthChart chart) {
        return new Yoga("DhanaYoga8", "DhanaYoga8", getCategory(), "Dhana yoga combo", "Wealth", List.of(), List.of(), 1.0, true, "Formed");
    }
}
