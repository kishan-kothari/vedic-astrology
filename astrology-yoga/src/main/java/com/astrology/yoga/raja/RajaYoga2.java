package com.astrology.yoga.raja;
import com.astrology.yoga.*;
import com.astrology.core.*;
import com.astrology.planets.BirthChart;
import java.util.List;
public class RajaYoga2 implements YogaRule {
    public String getYogaName() { return "RajaYoga2"; }
    public Yoga.YogaCategory getCategory() { return Yoga.YogaCategory.RAJA_YOGA; }
    public Yoga check(BirthChart chart) {
        return new Yoga("RajaYoga2", "RajaYoga2", getCategory(), "Raja yoga combo", "Power and success", List.of(), List.of(), 1.0, true, "Formed");
    }
}
