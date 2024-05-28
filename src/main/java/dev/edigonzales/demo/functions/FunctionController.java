package dev.edigonzales.demo.functions;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunctionController {
    private static final Logger logger = LoggerFactory.getLogger(FunctionController.class);

    private final ChatClient chatClient;

    public FunctionController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
    
    @GetMapping("/api/functions/cities")
    public String cityFaq(@RequestParam(value = "message") String message) {
        SystemMessage systemMessage = new SystemMessage("You are a helpful AI Assistant answering questions about cities around the world. Please respond in german and do not use sz but ss. And use the metric system.");
        UserMessage userMessage = new UserMessage(message);
        OpenAiChatOptions chatOptions = OpenAiChatOptions.builder()
                .withFunction("currentWeatherFunction")
                .build();
        ChatResponse response = chatClient.call(new Prompt(List.of(systemMessage,userMessage),chatOptions));

//        ChatResponse response = chatClient.call(new Prompt(List.of(systemMessage, userMessage)));
        return response.getResult().getOutput().getContent();
    }

    @GetMapping("/api/functions/oereb")
    public String oerebInfo(@RequestParam(value = "message") String message) {
        SystemMessage systemMessage = new SystemMessage("Du bist ein hilfsbereiter AI-Assistent und "
                + "beantwortest Fragen über die öffentlich-rechtlichen Eigentumsbeschränkungen (ÖREB) auf einem Grundstück."
                + "Please respond in german and do not use sz but ss."
                + "And use the metric system.");
        UserMessage userMessage = new UserMessage(message);
        
        OpenAiChatOptions chatOptions = OpenAiChatOptions.builder()
                .withFunctions(Set.of("grundstueckFunction", "oerebFunction"))
                .build();
        ChatResponse response = chatClient.call(new Prompt(List.of(systemMessage, userMessage), chatOptions));

//        ChatResponse response = chatClient.call(new Prompt(List.of(systemMessage, userMessage)));
        return response.getResult().getOutput().getContent();
    }


    
    

}
