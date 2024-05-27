package dev.edigonzales.demo.stuff;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrganisationController {
    private static final Logger logger = LoggerFactory.getLogger(OrganisationController.class);

    private final ChatClient chatClient;

    @Value("classpath:/prompts/organisation-kanton-solothurn.st")
    private Resource organisationKantonSolothurnResource;

    @Value("classpath:/docs/organisation-kanton-solothurn.md")
    private Resource docsToStuffResource;

    public OrganisationController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
    
    @GetMapping("/api/output/stuff/organisation")
    public String getOrganisationInfo(
            @RequestParam(value = "message", defaultValue = "Wer ist Amtschef oder Amtschefin im Amt für Umwelt des Kantons Solothurn") String message,
            @RequestParam(value = "stuffit", defaultValue = "false") boolean stuffit
            ) {
                
        PromptTemplate promptTemplate = new PromptTemplate(organisationKantonSolothurnResource);
        Map<String,Object> map = new HashMap<>();
        map.put("question", message);
        if(stuffit) {
            map.put("context", docsToStuffResource);
        } else {
            map.put("context", ""); 
            // Nicht ganz fair, da es nun gar keinen Context hat, oder? 
            // Nicht mal den global public one?
            // Man müsste eine Stufe weiter oben das if/else machen, also anderer Prompt.
        }
        
        Prompt prompt = promptTemplate.create(map);
        ChatResponse response = chatClient.call(prompt);
        
        return response.getResult().getOutput().getContent();
    }

}
