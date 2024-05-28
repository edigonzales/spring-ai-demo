package dev.edigonzales.demo.functions;

import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OerebService implements Function<OerebService.Request, OerebService.Response> {
    private static final Logger log = LoggerFactory.getLogger(OerebService.class);

    @Override
    public Response apply(OerebService.Request oerebRequest) {
        log.info("ÖREB request: {}", oerebRequest);
        // Hier würde der ÖREB-Webservice angefragt werden.
        Response response = new Response(oerebRequest.egrid, List.of(
                "Nutzungsplanung Grundnutzung (Rechtskräftig)", 
                "Nutzungsplanung überlagernd (Rechtskräftig)",
                "Baulinien (kantonal/kommunal) (Rechtskräftig)",
                "Lärmempfindlichkeitsstufen (in Nutzungszonen) (Rechtskräftig)",
                "Kataster der belasteten Standorte (Rechtskräftig)",
                "Kataster der belasteten Standorte im Bereich des öffentlichen Verkehrs (Rechtskräftig)",
                "Einzelschutz (Rechtskräftig)"
                ));
        log.info("ÖREB response: {}", response);
        return response;
    }
    
    public record Request(String egrid) {}
    public record Response(String grundbuchnummer, List<String> eigentumsbeschraenkungen) {}
}
