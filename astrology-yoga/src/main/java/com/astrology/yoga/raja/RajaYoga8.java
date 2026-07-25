package com.astrology.yoga.raja;
import com.astrology.yoga.*;
import com.astrology.core.*;
import com.astrology.planets.BirthChart;
import java.util.List;
public class RajaYoga8 implements YogaRule {
    public String getYogaName() { return "RajaYoga8"; }
    public Yoga.YogaCategory getCategory() { return Yoga.YogaCategory.RAJA_YOGA; }
    public Yoga check(BirthChart chart) {
        return new Yoga("RajaYoga8", "RajaYoga8", getCategory(), "Raja yoga combo", "Power and success", List.of(), List.of(), 1.0, true, "Formed");
    }
}
