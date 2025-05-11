package com.example.logparser;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@SpringBootApplication
public class LogParserApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogParserApplication.class, args);
    }

    @Bean
    CommandLineRunner parseLogFile(LogParserService logParserService) {
        return args -> {
            String filePath = "src/main/resources/logfile.txt";
            logParserService.parseLogFile(filePath);
        };
    }
}

// LogParserService - Service following SOLID principles
package com.example.logparser;

        import org.springframework.stereotype.Service;
        import java.io.IOException;
        import java.nio.file.Files;
        import java.nio.file.Path;
        import java.util.List;

@Service
public class LogParserService {

    public void parseLogFile(String filePath) throws IOException {
        Path path = Path.of(filePath);
        List<String> lines = Files.readAllLines(path);

        for (String line : lines) {
            LogEntryEntity logEntry = LogEntryEntityFactory.createLogEntryEntity(line);
            System.out.println(logEntry);
        }
    }
}

// LogEntryEntity class (using Factory Design Pattern)
package com.example.logparser;

public class LogEntryEntity {
    private final String timestamp;
    private final String taskName;
    private final String status;
    private final String taskId;

    public LogEntryEntity(String timestamp, String taskName, String status, String taskId) {
        this.timestamp = timestamp;
        this.taskName = taskName;
        this.status = status;
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "LogEntryEntity{" + "timestamp='" + timestamp + '\'' + ", taskName='" + taskName + '\'' + ", status='" + status + '\'' + ", taskId='" + taskId + '\'' + '}';
    }
}

// LogEntryEntityFactory (Factory Pattern)
package com.example.logparser;

public class LogEntryEntityFactory {

    public static LogEntryEntity createLogEntryEntity(String logLine) {
        String[] parts = logLine.split(",");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid log format");
        }
        return new LogEntryEntity(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim());
    }
}
