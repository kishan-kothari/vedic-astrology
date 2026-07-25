package com.astrology.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Vedic Astrology Engine API")
                .version("1.0.0")
                .description("Production-quality Jyotish calculation API with Swiss Ephemeris")
                .license(new License().name("MIT")))
            .addTagsItem(new Tag().name("Birth Chart").description("Natal chart calculations"))
            .addTagsItem(new Tag().name("Dashas").description("Planetary period systems"))
            .addTagsItem(new Tag().name("Yogas").description("Planetary combination rules"))
            .addTagsItem(new Tag().name("Strength").description("Shadbala and Ashtakavarga"))
            .addTagsItem(new Tag().name("Transit").description("Transit engine and Gochara"))
            .addTagsItem(new Tag().name("Reports").description("PDF, HTML, JSON reports"));
    }
}
