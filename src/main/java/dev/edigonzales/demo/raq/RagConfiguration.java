package dev.edigonzales.demo.raq;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class RagConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(RagConfiguration.class);

    @Value("classpath:/docs/organisation-kanton-solothurn.md")
    private Resource organisationKantonSolothurn;
    
    @Value("vectorstore.json")
    private String vectorStoreName; 

    @Bean
    SimpleVectorStore simpleVectorStore(EmbeddingClient embeddingClient) {
        SimpleVectorStore simpleVectorStore = new SimpleVectorStore(embeddingClient);
        File vectorStoreFile = getVectorStoreFile();
        if (vectorStoreFile.exists()) {
            logger.info("Vector Store File exists");
            simpleVectorStore.load(vectorStoreFile);
        } else {
            logger.info("Vector Store file does not exists, loading documents");
            TextReader textReader = new TextReader(organisationKantonSolothurn);
            textReader.getCustomMetadata().put("filename", "organisation-kanton-solothurn.md");
            List<Document> documents = textReader.get();
            TokenTextSplitter tokenTextSplitter = new TokenTextSplitter();
            List<Document> splitDocuments = tokenTextSplitter.apply(documents);
            
            simpleVectorStore.add(splitDocuments);
            simpleVectorStore.save(vectorStoreFile);
        }
        
        return simpleVectorStore;
    }
    
    private File getVectorStoreFile() {
        Path path = Paths.get("src", "main", "resources", "data");
        String absolutePath = path.toFile().getAbsolutePath() + "/" + vectorStoreName;
        return new File(absolutePath);
    }
}
