package log.monitor.service;


import log.monitor.config.AlertConfigInterface;
import log.monitor.entity.Alert;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public
class AlertService implements AlertServiceInterface {
    private static final Logger LOGGER = Logger.getLogger(AlertService.class.getName());
    private final long warningThreshold;
    private final long errorThreshold;


    public AlertService(AlertConfigInterface alertConfig) {
        this.warningThreshold = alertConfig.getWarningThreshold();
        this.errorThreshold = alertConfig.getErrorThreshold();
    }

    public void logDuration(String jobId, long duration) {
        String levelWarning = (duration > warningThreshold) ? "WARNING" : null;
        String level = (duration > errorThreshold) ? "ERROR" : levelWarning;
        if (level != null) {
            Alert alert = new Alert(jobId, duration, level);
            Level alertType = level.equals("ERROR") ? Level.SEVERE : Level.WARNING;
            LOGGER.log(alertType, alert.toString());
        }
    }
}
