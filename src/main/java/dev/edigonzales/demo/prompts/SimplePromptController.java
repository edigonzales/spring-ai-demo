package dev.edigonzales.demo.prompts;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimplePromptController {
    private static final Logger logger = LoggerFactory.getLogger(SimplePromptController.class);

    private final ChatClient chatClient;
    
    @Value("classpath:/prompts/youtube.st")
    private Resource ytPromptResource;
    
    public SimplePromptController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
    
    @GetMapping("/api/prompts/jokes")
    public String generate() {
        return chatClient.call(new Prompt("Tell me a dad joke")).getResult().getOutput().getContent();
    }

    @GetMapping("/api/prompts/youtubers")
    public String findPopularYouTubersByGenre(@RequestParam(value = "genre", defaultValue = "tech") String genre) {
//        String message = """
//                List 10 of the most popular YouTubers in {genre} along with their current subscriber counts. If you don't 
//                know the answer, just say "I don't know".
//                """;
//        PromptTemplate promptTemplate = new PromptTemplate(message);
        PromptTemplate promptTemplate = new PromptTemplate(ytPromptResource);
        Prompt prompt = promptTemplate.create(Map.of("genre", genre));
        return chatClient.call(prompt).getResult().getOutput().getContent();
    } 
    
    @GetMapping("/api/prompts/dad-jokes")
    public String jokes() {
        SystemMessage system = new SystemMessage("Your primary function is to tell dad jokes. If someone asks you for any other type of jokes please tell them you only know dad jokes.");
//        Message user = new UserMessage("Tell me a joke about cats");
        Message user = new UserMessage("Tell me a serious joke about the universe.");
//        Prompt prompt = new Prompt(user);
        Prompt prompt = new Prompt(List.of(system, user));
        return chatClient.call(prompt).getResult().getOutput().getContent();
    }
    
    
    
    
}
