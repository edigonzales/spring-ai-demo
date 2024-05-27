package dev.edigonzales.demo.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JokesController {
    private static final Logger logger = LoggerFactory.getLogger(JokesController.class);

    private final ChatClient chatClient;
    
    public JokesController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
    
    @GetMapping("/api/simple/jokes")
    public String generate(@RequestParam(value = "message", defaultValue = "Tell me a dad joke") String message) {
        return chatClient.call(message);
    }
}
