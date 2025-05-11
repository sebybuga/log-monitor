package log.monitor;

import log.monitor.service.LogParserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LogMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogMonitorApplication.class, args);
	}

	@Bean
	CommandLineRunner parseLogFile(LogParserService logParserService) {
		return args -> {
			String filePath = "src/main/resources/logs.log";
			logParserService.parseLogFile(filePath);
		};
	}

}
