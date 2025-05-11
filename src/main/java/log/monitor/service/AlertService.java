package log.monitor.service;


import log.monitor.config.AlertConfigInterface;
import log.monitor.entity.Alert;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class generates console messages related to Alerts
 */
@Service
class AlertService implements AlertServiceInterface {
    private static final Logger logger =Logger.getLogger(AlertService.class.getName());
    private final long warningThreshold;
    private final long errorThreshold;


    public AlertService(AlertConfigInterface alertConfig) {
        this.warningThreshold = alertConfig.getWarningThreshold();
        this.errorThreshold = alertConfig.getErrorThreshold();
    }

    public Level logDuration(String jobId, long duration) {

        String levelWarning = (duration > warningThreshold) ? "WARNING" : null;
        String level = (duration > errorThreshold) ? "ERROR" : levelWarning;
        if (level != null) {
            Alert alert = new Alert(jobId, duration, level);
            Level alertType = level.equals("ERROR") ? Level.SEVERE : Level.WARNING;
            logger.log(alertType, alert.toString());
            return alertType;
        }
        return null;
    }
}
