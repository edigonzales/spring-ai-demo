package dev.edigonzales.demo.functions;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

@Configuration
public class GrunstueckFunctionConfiguration {
    @Bean
    @Description("Get the EGRID number by parcel number and municipality name.")
    public Function<GrundstueckService.Request, GrundstueckService.Response> grundstueckFunction() {
        return new GrundstueckService();
    }
}
