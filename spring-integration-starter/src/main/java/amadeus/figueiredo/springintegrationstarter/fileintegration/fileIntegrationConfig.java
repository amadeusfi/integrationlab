package amadeus.figueiredo.springintegrationstarter.fileintegration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;

import java.io.File;

@Configuration
@EnableIntegration
public class fileIntegrationConfig {

    @Bean
    @InboundChannelAdapter(value = "fileInputChannel", poller = @Poller(fixedDelay = "1000"))
    public FileReadingMessageSource fileReadingMessageSource() {
        FileReadingMessageSource reader = new FileReadingMessageSource();
        reader.setDirectory(new File("spring-integration-starter/src/main/resources/autocreated_dir_import_source"));
        return reader;
    }
    @Bean
    @ServiceActivator(inputChannel = "fileInputChannel")
    public FileWritingMessageHandler fileWritingMessageHandler() {
        FileWritingMessageHandler writer = new FileWritingMessageHandler(new File("spring-integration-starter/src/main/resources/autocreated_dir_import_destination"));
        writer.setAutoCreateDirectory(true);
        writer.setExpectReply(false);
        return writer;
    }

    @Bean
    @InboundChannelAdapter(value = "fileInputChannelFilteredPdf", poller = @Poller(fixedDelay = "1000"))
    public FileReadingMessageSource fileReadingMessageSourcePdf() {
        CompositeFileListFilter<File> filter = new CompositeFileListFilter<>();
        filter.addFilter(new SimplePatternFileListFilter("*.pdf"));
        FileReadingMessageSource reader = new FileReadingMessageSource();
        reader.setDirectory(new File("spring-integration-starter/src/main/resources/autocreated_dir_import_source"));
        reader.setFilter(filter);
        return reader;
    }

    @Bean
    @ServiceActivator(inputChannel = "fileInputChannelFilteredPdf")
    public FileWritingMessageHandler fileWritingMessageHandlerPdf() {
        FileWritingMessageHandler writerPdf = new FileWritingMessageHandler(new File("spring-integration-starter/src/main/resources/autocreated_dir_import_pdf_destination"));
        writerPdf.setAutoCreateDirectory(true);
        writerPdf.setExpectReply(false);
        return writerPdf;
    }
}
