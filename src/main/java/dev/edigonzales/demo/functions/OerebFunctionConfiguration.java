package dev.edigonzales.demo.functions;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

@Configuration
public class OerebFunctionConfiguration {
//    private final WeatherConfigProperties props;

//    public OerebFunctionConfiguration(WeatherConfigProperties props) {
//        this.props = props;
//    }

    @Bean
    @Description("Get the current ÖREB information for a given parcel by egrid.")
    //@Description("Liefert die öffentlich-rechtlichen Eigentumsbeschränkungen für ein bestimmtes Grundstück mittels EGRID.")
    public Function<OerebService.Request, OerebService.Response> oerebFunction() {
        return new OerebService();
    }
}
