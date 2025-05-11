package log.monitor.config;

import log.monitor.service.AlertService;
import log.monitor.service.LogParserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false) // Disable CGLIB proxying
public class AppConfig {

    @Bean
    public AlertConfigInterface alertConfig() {
        // Assuming the properties file is "application.properties" in the classpath
        return new AlertConfig("application.properties");
    }

    @Bean
    public AlertService alertService(AlertConfigInterface alertConfig) {
        return new AlertService(alertConfig);
    }

    @Bean
    public LogParserService logParserService(AlertService alertService) {
        return new LogParserService(alertService);
    }
}
