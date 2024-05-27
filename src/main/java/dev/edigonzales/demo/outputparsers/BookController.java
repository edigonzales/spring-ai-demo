package dev.edigonzales.demo.outputparsers;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.ai.parser.MapOutputParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(SongsController.class);

    private final ChatClient chatClient;

    public BookController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/api/output/books/author/{author}")
    public Map<String, Object> getAuthorsSocialLinks(@PathVariable(name = "author") String author) {
        String promptMessage = """
                Generate a list of links for the author {author}. Include the authors name as the key and any social network links as object.
                {format}
                """;
        
        MapOutputParser mapOutputParser = new MapOutputParser();
        String format = mapOutputParser.getFormat();
        
        PromptTemplate promptTemplate = new PromptTemplate(promptMessage, Map.of("author", author, "format", format));
        Prompt prompt = promptTemplate.create();
        Generation generation = chatClient.call(prompt).getResult();
        return mapOutputParser.parse(generation.getOutput().getContent());
    } 

    
    @GetMapping("/api/output/books/by-author")
    public Author getBooksByAuthor(@RequestParam(value = "author", defaultValue = "George Orwell") String author) {
        String promptMessage = """
                Generate a list of books written by the author {author}. If you aren't positive that a book belongs to this author, 
                please don't include it.
                {format}
                """;
        
        var beanOutputParser = new BeanOutputParser<>(Author.class);
        String format = beanOutputParser.getFormat();
        
        PromptTemplate promptTemplate = new PromptTemplate(promptMessage, Map.of("author", author, "format", format));
        Prompt prompt = promptTemplate.create();
        Generation generation = chatClient.call(prompt).getResult();
        return beanOutputParser.parse(generation.getOutput().getContent());
    } 

}
