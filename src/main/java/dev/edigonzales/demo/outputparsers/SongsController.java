package dev.edigonzales.demo.outputparsers;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.parser.ListOutputParser;
import org.springframework.ai.parser.OutputParser;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.edigonzales.demo.prompts.SimplePromptController;

@RestController
public class SongsController {
    private static final Logger logger = LoggerFactory.getLogger(SongsController.class);

    private final ChatClient chatClient;

    public SongsController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }


    @GetMapping("/api/output/songs")
    public List<String> getSongsByArtist(@RequestParam(value = "artist", defaultValue = "The Beatles") String artist) {
        String message = """
                Please give me a list of top 10 songs for the artist {artist}. If you don't know the answer, 
                just say "I don't know".
                {format}
                """;
        
        ListOutputParser outputParser = new ListOutputParser(new DefaultConversionService());
        PromptTemplate promptTemplate = new PromptTemplate(message);
        Prompt prompt = promptTemplate.create(Map.of("artist", artist, "format", outputParser.getFormat()));
        ChatResponse response = chatClient.call(prompt);
        return outputParser.parse(response.getResult().getOutput().getContent());
    } 
    
//    @GetMapping("/api/output/songs")
//    public String getSongsByArtist(@RequestParam(value = "artist", defaultValue = "Paul Weller") String artist) {
//        String message = """
//                Please give me a list of top 10 songs for the artist {artist}. If you don't know the answer, 
//                just say "I don't know".
//                """;
//        PromptTemplate promptTemplate = new PromptTemplate(message);
//        Prompt prompt = promptTemplate.create(Map.of("artist", artist));
//        ChatResponse response = chatClient.call(prompt);
//        return response.getResult().getOutput().getContent();
//    } 


}
