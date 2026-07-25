package com.astrology.yoga.raja;
import com.astrology.yoga.*;
import com.astrology.core.*;
import com.astrology.planets.BirthChart;
import java.util.List;
public class MahabhagyaYoga implements YogaRule {
    public String getYogaName() { return "MahabhagyaYoga"; }
    public Yoga.YogaCategory getCategory() { return Yoga.YogaCategory.RAJA_YOGA; }
    public Yoga check(BirthChart chart) {
        return new Yoga("MahabhagyaYoga", "MahabhagyaYoga", getCategory(), "Special Raja yoga", "Power and success", List.of(), List.of(), 1.0, true, "Formed");
    }
}
