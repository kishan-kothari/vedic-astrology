package com.astrology.api.config;

import com.astrology.core.EphemerisConfig;
import com.astrology.core.AyanamshaType;
import com.astrology.core.HouseSystem;
import com.astrology.core.SwissEphemerisService;
import com.astrology.planets.PlanetaryCalculator;
import com.astrology.divisional.DivisionalEngine;
import com.astrology.dasha.DashaEngine;
import com.astrology.yoga.YogaEngine;
import com.astrology.strength.ShadbalaCalculator;
import com.astrology.strength.AshtakavargaEngine;
import com.astrology.transit.TransitEngine;
import com.astrology.report.ReportEngine;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AstrologyConfig {

    @Value("${astrology.ephe-path:./ephe}")
    private String ephePath;

    @Value("${astrology.ayanamsha:LAHIRI}")
    private String ayanamsha;

    @Bean
    public EphemerisConfig ephemerisConfig() {
        AyanamshaType ayanamshaType;
        try {
            ayanamshaType = AyanamshaType.valueOf(ayanamsha.toUpperCase());
        } catch (IllegalArgumentException e) {
            ayanamshaType = AyanamshaType.LAHIRI;
        }
        return new EphemerisConfig(ephePath, ayanamshaType, HouseSystem.WHOLE_SIGN,
            de.thmac.swisseph.SweConst.SEFLG_SWIEPH | de.thmac.swisseph.SweConst.SEFLG_SPEED | de.thmac.swisseph.SweConst.SEFLG_SIDEREAL);
    }
    
    @Bean
    public SwissEphemerisService swissEphemerisService(EphemerisConfig config) {
        return new SwissEphemerisService(config);
    }
    
    @Bean
    public PlanetaryCalculator planetaryCalculator(EphemerisConfig config) { 
        return new PlanetaryCalculator(config);
    }
    
    @Bean
    public DivisionalEngine divisionalEngine() { 
        return new DivisionalEngine(); 
    }
    
    @Bean
    public DashaEngine dashaEngine() { 
        return new DashaEngine(); 
    }
    
    @Bean
    public YogaEngine yogaEngine() { 
        return new YogaEngine(); 
    }
    
    @Bean
    public ShadbalaCalculator shadbalaCalculator() { 
        return new ShadbalaCalculator(); 
    }
    
    @Bean
    public AshtakavargaEngine ashtakavargaEngine() { 
        return new AshtakavargaEngine(); 
    }
    
    @Bean
    public TransitEngine transitEngine(EphemerisConfig config) { 
        return new TransitEngine(config); 
    }
    
    @Bean
    public ReportEngine reportEngine(
            PlanetaryCalculator planetCalc,
            DivisionalEngine divisionalEngine,
            DashaEngine dashaEngine,
            YogaEngine yogaEngine,
            ShadbalaCalculator shadbalaCalculator,
            AshtakavargaEngine ashtakavargaEngine) { 
        return new ReportEngine(planetCalc, divisionalEngine, dashaEngine, yogaEngine, shadbalaCalculator, ashtakavargaEngine);
    }
}
