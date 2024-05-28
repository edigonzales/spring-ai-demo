package dev.edigonzales.demo.functions;

import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrundstueckService  implements Function<GrundstueckService.Request, GrundstueckService.Response> {
    private static final Logger log = LoggerFactory.getLogger(GrundstueckService.class);

    @Override
    public Response apply(GrundstueckService.Request request) {
        log.info("Grundstueck request: {}", request);
        Response response = new Response(request.gemeinde, request.grundstuecksnummer, "CH788232067709");
        log.info("Grundstueck response: {}", response);
        return response;
    }

    public record Request(String gemeinde, String grundstuecksnummer) {}
    public record Response(String gemeinde, String grundstuecksnummer, String egrid) {}
}
