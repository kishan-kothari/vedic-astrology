package com.astrology.yoga.viparita;
import com.astrology.yoga.*;
import com.astrology.core.*;
import com.astrology.planets.BirthChart;
import java.util.List;
public class VimalaYoga implements YogaRule {
    public String getYogaName() { return "VimalaYoga"; }
    public Yoga.YogaCategory getCategory() { return Yoga.YogaCategory.VIPARITA_RAJA_YOGA; }
    public Yoga check(BirthChart chart) {
        return new Yoga("VimalaYoga", "VimalaYoga", getCategory(), "Viparita Raja Yoga", "Success after struggle", List.of(), List.of(), 1.0, true, "Formed");
    }
}
