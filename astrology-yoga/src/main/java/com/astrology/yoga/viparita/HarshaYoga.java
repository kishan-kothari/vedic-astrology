package com.astrology.yoga.viparita;
import com.astrology.yoga.*;
import com.astrology.core.*;
import com.astrology.planets.BirthChart;
import java.util.List;
public class HarshaYoga implements YogaRule {
    public String getYogaName() { return "HarshaYoga"; }
    public Yoga.YogaCategory getCategory() { return Yoga.YogaCategory.VIPARITA_RAJA_YOGA; }
    public Yoga check(BirthChart chart) {
        return new Yoga("HarshaYoga", "HarshaYoga", getCategory(), "Viparita Raja Yoga", "Success after struggle", List.of(), List.of(), 1.0, true, "Formed");
    }
}
