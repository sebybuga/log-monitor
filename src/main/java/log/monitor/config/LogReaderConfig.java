package log.monitor.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Configuration
public class LogReaderConfig {

    @Value("${log.file.path}")
    private String filePath;

    @Bean
    public BufferedReader bufferedReader() throws IOException {
        return new BufferedReader(new FileReader(filePath));
    }
}
