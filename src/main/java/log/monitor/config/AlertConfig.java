package log.monitor.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class AlertConfig implements AlertConfigInterface {

    private long warningThreshold;
    private long errorThreshold;

    public AlertConfig (String filePath) {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new RuntimeException("Properties file not found in classpath: " + filePath);
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error loading application properties", e);
        }

        this.warningThreshold = Long.parseLong(properties.getProperty("threshold.warning.seconds", "300"));
        this.errorThreshold = Long.parseLong(properties.getProperty("threshold.error.seconds", "600"));


    }
    @Override
    public long getWarningThreshold() {
        return warningThreshold;
    }
    @Override
    public long getErrorThreshold() {
        return errorThreshold;
    }
}
