package log.monitor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class AppConfig {

    @Bean
    public AlertConfigInterface alertConfig() {
        return new AlertConfig("application.properties");
    }
}
