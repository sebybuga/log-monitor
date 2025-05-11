package log.monitor.config;

import org.springframework.beans.factory.annotation.Value;


public class AlertConfig implements AlertConfigInterface {
    @Value("${threshold.warning.seconds}")
    private long warningThreshold;

    @Value("${threshold.error.seconds}")
    private long errorThreshold;


    @Override
    public long getWarningThreshold() {
        return warningThreshold;
    }
    @Override
    public long getErrorThreshold() {
        return errorThreshold;
    }
}
