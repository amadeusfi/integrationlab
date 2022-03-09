package amadeus.figueiredo.springintegrationstarter.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;

import java.io.File;

@EnableIntegration
@Configuration
public class IntegrationConfig {

    @Autowired
    public Transformer transformer;

    @Bean
    public IntegrationFlow integrationFlow() {
        return IntegrationFlows
                .from(fileReader(),
                e -> e.poller(Pollers.fixedDelay(500)))
                .transform(transformer, "transform")
                .handle(fileWriter())
                .get();

    }

    // adapter
    @Bean
    public FileWritingMessageHandler fileWriter() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(
                new File("spring-integration-starter/src/main/resources/no_autocreated_dir_import_destination")
        );
        handler.setExpectReply(false);
        return handler;
    }

    // adapter
    @Bean
    public FileReadingMessageSource fileReader() {
        FileReadingMessageSource source = new FileReadingMessageSource();
        source.setDirectory(new File("spring-integration-starter/src/main/resources/no_autocreated_dir_import_source"));
        return source;
    }


}
