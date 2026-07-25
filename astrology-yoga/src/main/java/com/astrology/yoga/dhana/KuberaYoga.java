package com.astrology.yoga.dhana;
import com.astrology.yoga.*;
import com.astrology.core.*;
import com.astrology.planets.BirthChart;
import java.util.List;
public class KuberaYoga implements YogaRule {
    public String getYogaName() { return "KuberaYoga"; }
    public Yoga.YogaCategory getCategory() { return Yoga.YogaCategory.DHANA_YOGA; }
    public Yoga check(BirthChart chart) {
        return new Yoga("KuberaYoga", "KuberaYoga", getCategory(), "Special Dhana yoga", "Wealth", List.of(), List.of(), 1.0, true, "Formed");
    }
}
