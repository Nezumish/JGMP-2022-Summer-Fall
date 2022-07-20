package org.example.search.engines.impl.config;

import nl.siegmann.epublib.epub.EpubReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EpubToolsConfiguration {

    @Bean
    public EpubReader epubReader() {
        return new EpubReader();
    }

}
